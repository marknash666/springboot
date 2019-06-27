package org.fisco.bcos;

import java.math.BigInteger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.temp.LAGCredit;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LAGCreditController {

    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;

    LAGCreditController(Web3j web3js_1, Credentials credentials_1) {
        web3j = web3js_1;
        credentials = credentials_1;
    }

    public LAGCredit deploy() {
        LAGCredit lagCredit = null;
        try {
            lagCredit =
                    LAGCredit.deploy(
                                    web3j,
                                    credentials,
                                    new StaticGasProvider(
                                            GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT),
                                    new BigInteger("100000"),
                                    "LAGC",
                                    "LAG")
                            .send();
            log.info("LAGC address is {}", lagCredit.getContractAddress());
            return lagCredit;
        } catch (Exception e) {
            log.error("deploy fail: {}", e.getMessage());
        }
        return lagCredit;
    }

    public boolean transfer(String creditAddress, String to, BigInteger value) {
        try {
            LAGCredit lagCredit = load(creditAddress);
            TransactionReceipt receipt = lagCredit.transfer(to, value).send();
            log.info("status : {}", receipt.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public LAGCredit load(String creditAddress) {
        LAGCredit lagCredit =
                LAGCredit.load(
                        creditAddress,
                        web3j,
                        credentials,
                        new StaticGasProvider(GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
        return lagCredit;
    }

    public long getBalanceByOwner(String creditAddress, String owner) {
        BigInteger balance;
        try {
            LAGCredit lagCredit = load(creditAddress);
            balance = lagCredit.balanceOf(owner).send();
            return balance.longValue();
        } catch (Exception e) {
            log.error("getbalance fail: {}", e.getMessage());
        }

        return 0;
    }

    @RequestMapping(
            value = "/getCurrentSupply",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public String getBalanceOfCurrentUser_web(@RequestParam("address") String creditAddress) {

        try {

            long balance = getBalanceOfCurrentUser(creditAddress);

            JSONObject result = new JSONObject();
            result.put("supply", balance);
            return result.toString();
        } catch (Exception e) {
            log.error("getbalance fail: {}", e.getMessage());
        }

        return null;
    }

    public long getBalanceOfCurrentUser(@RequestParam("address") String creditAddress) {
        BigInteger balance;
        try {
            LAGCredit lagCredit = load(creditAddress);
            balance = lagCredit.balanceOfCur_user().send();

            return balance.longValue();
        } catch (Exception e) {
            log.error("getbalance fail: {}", e.getMessage());
        }

        return 0;
    }

    public String getContractOwner(String creditAddress) {

        try {
            LAGCredit lagCredit = load(creditAddress);
            String result = lagCredit.getContractOwner().send();
            return result;
        } catch (Exception e) {
            log.error("getContractOwner failed.", e.getMessage());
        }

        return null;
    }

    @RequestMapping(value = "/deployLAG", method = RequestMethod.GET)
    public String deploy_web() {
        LAGCredit lagCredit = deploy();
        JSONObject result = new JSONObject();
        result.put("address", lagCredit.getContractAddress());
        return result.toString();
    }

    @RequestMapping(
            value = "/transferLAG",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String transfer_web(@RequestBody TransferParam param) {
        String creditAddress = param.getAddress();
        String to = param.getTo();
        BigInteger value = param.getValue();
        boolean state = transfer(creditAddress, to, value);
        if (state) {
            String response1 =
                    ("The supply in the sender's account is "
                            + getBalanceOfCurrentUser(creditAddress));
            String response2 =
                    ("The supply in the receiver's account is "
                            + getBalanceByOwner(creditAddress, to));

            JSONObject result = new JSONObject();
            result.put("msg1", response1);
            result.put("msg2", response2);

            return result.toString();
        }
        JSONObject result = new JSONObject();
        result.put("msg", "transfer failed");
        return result.toString();
    }

    @RequestMapping(value = "/getTotal", method = RequestMethod.GET)
    public long getTotalSupply(@RequestParam("address") String creditAddress) {
        try {
            LAGCredit lagCredit = load(creditAddress);
            BigInteger total = lagCredit.getTotalSupply().send();
            return total.longValue();
        } catch (Exception e) {
            log.error("getTotalSupply fail: {}", e.getMessage());
        }
        return 0;
    }

    @Data
    static class TransferParam {

        String address;
        String to;
        BigInteger value;
    }
}
