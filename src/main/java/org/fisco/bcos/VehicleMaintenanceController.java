package org.fisco.bcos;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.temp.VehicleQuery;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class VehicleMaintenanceController {

    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;

    VehicleMaintenanceController(Web3j web3js_1, Credentials credentials_1) {
        web3j = web3js_1;
        credentials = credentials_1;
    }

    public VehicleQuery deploy() {
        VehicleQuery vehiclequery = null;
        try {
            vehiclequery =
                    VehicleQuery.deploy(
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

    public VehicleQuery load(String creditAddress) {
        VehicleQuery vehiclequery =
                VehicleQuery.load(
                        creditAddress,
                        web3j,
                        credentials,
                        new StaticGasProvider(GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
        return vehiclequery;
    }

    public boolean addApprovedMaintenanceShop(String creditAddress, String approvedAddress) {
        try {
            VehicleQuery vehiclequery = load(creditAddress);
            vehiclequery.addApprovedMaintenanceShop(approvedAddress).send();
            return true;
        } catch (Exception e) {
            log.error(
                    "The Addition of Approved MaintenanceShopls address failed: {}",
                    e.getMessage());
        }
        return false;
    }

    @RequestMapping(value = "/deploy", method = RequestMethod.GET)
    public String deploy_web() {
        VehicleQuery vehiclequery = deploy();
        JSONObject result = new JSONObject();
        result.put("address", vehiclequery.getContractAddress());
        return result.toString();
    }

    @Data
    static class TransferParam {

        String address;
        String approved;
    }

    @RequestMapping(
            value = "/addApprovedMaintenanceShop",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String deploy_web(@RequestBody TransferParam param) {

        boolean state = addApprovedMaintenanceShop(param.address, param.approved);
        if (state) {
            String response1 = ("Successfully add an approved address of MaintenanceShop");
            JSONObject result = new JSONObject();
            result.put("msg", response1);
            return result.toString();
        }
        JSONObject result = new JSONObject();
        result.put("msg", "The Addition of Approved MaintenanceShopls address failed.");
        return result.toString();
    }
}
