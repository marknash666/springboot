package org.fisco.bcos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.temp.VehicleOwnership;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple4;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class VehicleMaintenanceController {

    @Autowired private Web3j web3j;
    private Credentials credentials;

    private String userKey;

    public void changeUser(String userKey) throws Exception {
       this.userKey=userKey;
    }

    public Credentials getCredentials() throws Exception {
        log.info("userKey : {}", userKey);
        Credentials credentials = GenCredential.create(userKey);
        if (credentials == null) {
            throw new Exception("create Credentials failed");
        }
        return credentials;
    }

    VehicleMaintenanceController(Web3j web3js_1, Credentials credentials_1) {
        web3j = web3js_1;
        credentials = credentials_1;
    }

    public VehicleOwnership deploy() {
        VehicleOwnership vehiclequery = null;
        try {
            vehiclequery =
                    VehicleOwnership.deploy(
                                    web3j,
                                    credentials,
                                    new StaticGasProvider(
                                            GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT))
                            .send();
            log.info("VehicleMaintenance address is {}", vehiclequery.getContractAddress());
            return vehiclequery;
        } catch (Exception e) {
            log.error("deploy fail: {}", e.getMessage());
        }
        return vehiclequery;
    }

    public VehicleOwnership load(String creditAddress) throws Exception {
        credentials = getCredentials();
        VehicleOwnership vehiclequery =
                VehicleOwnership.load(
                        creditAddress,
                        web3j,
                        credentials,
                        new StaticGasProvider(GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
        return vehiclequery;
    }

    @RequestMapping(
            value = "/manufactureInit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String manufactureInit(@RequestBody InitParam param) {
        JSONObject result = new JSONObject();
        try {
            VehicleOwnership vehicleOwnership = load(param.address);
            log.info("asdadsdadadada" + param.vin + param.originInfo);
            TransactionReceipt receipt =
                    vehicleOwnership.ManufactureInit(param.vin, param.originInfo).send();
            result.put("status", receipt.isStatusOK());
            return result.toString();

        } catch (Exception e) {
            log.error(
                    "Initiation of the VehicleInfo of "
                            + param.vin
                            + param.originInfo
                            + " failed: {}",
                    e.getMessage());
        }
        result.put("status", false);
        return result.toString();
    }

    @Data
    static class InitParam {
        String address;
        String vin;
        String originInfo;

        @JsonCreator
        public InitParam(
                @JsonProperty("address") String address,
                @JsonProperty("vin") String vin,
                @JsonProperty("originInfo") String originInfo) {
            this.address = address;
            this.vin = vin;
            this.originInfo = originInfo;
        }
    }

    @RequestMapping(
            value = "/updateVehicleMaintenance",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String updateVehicleMaintenance(@RequestBody UpdateParam param) throws Exception {
        VehicleOwnership vehiclequery = load(param.address);
        TransactionReceipt receipt =
                vehiclequery.updateVehicleMaintenance(param.vin, param.remarks, param.info).send();
        JSONObject result = new JSONObject();
        result.put("status", receipt.isStatusOK());
        return result.toString();
    }

    @Data
    static class UpdateParam {
        String address;
        String vin;
        String remarks;
        String info;
    }

    @RequestMapping(value = "/deploy", method = RequestMethod.GET)
    public String deploy_web() {
        VehicleOwnership vehiclequery = deploy();
        JSONObject result = new JSONObject();
        result.put("address", vehiclequery.getContractAddress());
        return result.toString();
    }

    @RequestMapping(
            value = "/addApprovedMaintenanceShop",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String addApproved_web(@RequestBody AddApprovedParam param) throws Exception {
        VehicleOwnership vehiclequery = load(param.address);
        TransactionReceipt receipt = vehiclequery.addApprovedMaintenanceShop(param.approved).send();
        JSONObject result = new JSONObject();
        result.put("status", receipt.isStatusOK());
        return result.toString();
    }

    @Data
    static class AddApprovedParam {
        String address;
        String approved;
    }

    @RequestMapping(
            value = "/getVehicleTotalInfo",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public String getVehicleTotalInfo(
            @RequestParam("address") String creditAddress, @RequestParam("VIN") String VIN) {
        try {

            VehicleOwnership vehiclequery = load(creditAddress);
            BigInteger numsofrecords = vehiclequery.getNumsOfRecords(VIN).send();
            BigInteger index = BigInteger.valueOf(0);
            JSONObject result = new JSONObject();
            result.put("ManufactureInfo", vehiclequery.getVehicleManufacturingInfo(VIN).send());
            JSONArray jsona = new JSONArray();
            for (; !numsofrecords.equals(index); index = index.add(BigInteger.valueOf(1))) {
                // log.info("getVehicleTotalInfo failed+" + index + numsofrecords);
                JSONObject temp = new JSONObject();
                Tuple4<String, String, String, BigInteger> response =
                        vehiclequery.getTotalInfo(VIN, index).send();
                temp.put("MaintenanceInfo", response.getValue1());
                temp.put("remark", response.getValue2());
                temp.put("MaintenanceShopAddress", response.getValue3());
                temp.put("TimeStamp", response.getValue4());
                jsona.add(temp);
                // log.info("getVehicleTotalInfo failed+" + temp);
            }
            result.put("Records", jsona);
            return result.toString();
        } catch (Exception e) {
            log.error("getVehicleTotalInfo failed: {}", e.getMessage());
        }
        return null;
    }

    @RequestMapping(
            value = "/getExistence",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public String getExistence(
            @RequestParam("address") String creditAddress, @RequestParam("VIN") String VIN)
            throws Exception {
        VehicleOwnership vehiclequery = load(creditAddress);
        JSONObject result = new JSONObject();
        result.put("existence", vehiclequery.getExistence(VIN).send());
        return result.toString();
    }

    @RequestMapping(
            value = "/balanceOf",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public String balanceOf(
            @RequestParam("address") String creditAddress, @RequestParam("useraddress") String userAddress)
            throws Exception {
        VehicleOwnership vehiclequery = load(creditAddress);
        JSONObject result = new JSONObject();
        result.put("balance", vehiclequery.balanceOf(userAddress).send());

        return result.toString();
    }

    @RequestMapping(
            value = "/ownerOf",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public String ownerOf(
            @RequestParam("address") String creditAddress, @RequestParam("vin") String VIN)
            throws Exception {
        VehicleOwnership vehiclequery = load(creditAddress);
        JSONObject result = new JSONObject();
        result.put("owner", vehiclequery.ownerOf(VIN).send());
        return result.toString();
    }


    @Data
    static class TransferParam {
        String address;
        String vin;
        String toaddress;
    }
    @RequestMapping(
            value = "/transfer",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String transfer(
            @RequestBody TransferParam  param)
            throws Exception {

        VehicleOwnership vehiclequery = load(param.address);

        TransactionReceipt receipt =
                vehiclequery.transfer(param.toaddress, param.vin).send();
        JSONObject result = new JSONObject();
        result.put("status", receipt.isStatusOK());
        return result.toString();
    }

    @Data
    static class ApproveParam {
        String address;
        String vin;
        String toaddress;
    }
    @RequestMapping(
            value = "/approve",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String approve(
            @RequestBody ApproveParam  param)
            throws Exception {

        VehicleOwnership vehiclequery = load(param.address);
        TransactionReceipt receipt =
                vehiclequery.approve(param.toaddress, param.vin).send();
        JSONObject result = new JSONObject();
        result.put("status", receipt.isStatusOK());
        return result.toString();
    }

    @Data
    static class TakeOwnershipParam {
        String address;
        String vin;
    }
    @RequestMapping(
            value = "/takeOwnership",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String takeOwnership(
            @RequestBody TakeOwnershipParam  param)
            throws Exception {

        VehicleOwnership vehiclequery = load(param.address);
        TransactionReceipt receipt =
                vehiclequery.takeOwnership(param.vin).send();
        JSONObject result = new JSONObject();
        result.put("status", receipt.isStatusOK());
        return result.toString();
    }
}
