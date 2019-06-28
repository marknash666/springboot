package org.fisco.bcos;

import java.math.BigInteger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.temp.VehicleQuery;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(
            value = "/manufactureInit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public boolean manufactureInit(@RequestBody InitParam param) {
        try {
            VehicleQuery vehiclequery = load(param.address);
            log.info("VehicleMaintenance address is {}", vehiclequery.getContractAddress());
            vehiclequery.ManufactureInit(param.vin, "23232323").send();

            return true;
        } catch (Exception e) {
            log.error(
                    "Initiation of the VehicleInfo of "
                            + param.vin
                            + param.originInfo
                            + " failed: {}",
                    e.getMessage());
        }
        return false;
    }

    @Data
    static class InitParam {
        String address;
        String vin;
        String originInfo;
    }

    @RequestMapping(
            value = "/updateVehicleMaintenance",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public boolean updateVehicleMaintenance(@RequestBody UpdateParam param) {
        try {
            VehicleQuery vehiclequery = load(param.address);
            vehiclequery.updateVehicleMaintenance(param.vin, param.remarks, param.info).send();
            return true;
        } catch (Exception e) {
            log.error(
                    "Update of the Maintenance record of " + param.vin + " failed: {}",
                    e.getMessage());
        }
        return false;
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
        VehicleQuery vehiclequery = deploy();
        JSONObject result = new JSONObject();
        result.put("address", vehiclequery.getContractAddress());
        return result.toString();
    }

    @RequestMapping(
            value = "/addApprovedMaintenanceShop",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String addApproved_web(@RequestBody AddApprovedParam param) {
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
            VehicleQuery vehiclequery = load(creditAddress);
            BigInteger numsofrecords = vehiclequery.getNumsOfRecords(VIN).send();
            BigInteger index = BigInteger.valueOf(0);
            JSONObject result = new JSONObject();
            result.put("ManufactureInfo", vehiclequery.getVehicleManufacturingInfo(VIN).send());
            JSONArray jsona = new JSONArray();
            for (; !numsofrecords.equals(index); index = index.add(BigInteger.valueOf(1))) {
                log.info("getVehicleTotalInfo failed+" + index + numsofrecords);
                JSONObject temp = new JSONObject();
                temp.put("MaintenanceInfo", vehiclequery.getInfo(VIN, index).send());
                temp.put("remark", vehiclequery.getRemark(VIN, index).send());
                temp.put("MaintenanceShopAddress", vehiclequery.getAddress(VIN, index).send());
                temp.put("TimeStamp", vehiclequery.getTimeStamp(VIN, index).send());
                jsona.add(temp);
                log.info("getVehicleTotalInfo failed+" + temp);
            }
            result.put("Records", jsona);
            return result.toString();
        } catch (Exception e) {
            log.error("getVehicleTotalInfo failed: {}", e.getMessage());
        }
        return null;
    }
}
