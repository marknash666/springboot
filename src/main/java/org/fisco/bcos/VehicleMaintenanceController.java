package org.fisco.bcos;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.temp.VehicleQuery;
import org.fisco.bcos.web3j.crypto.Credentials;
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
    public TransactionReceipt manufactureInit_web(@RequestBody InitParam param) {
        try {
            VehicleQuery vehiclequery = load(param.address);
            TransactionReceipt receipt=vehiclequery.ManufactureInit(param.vin, param.originInfo).send();
            log.info("23233 ",receipt.isStatusOK());
            log.info("23233 ",receipt.getOutput());
            log.info("23233 ",receipt.getStatus());
            return receipt;

        } catch (Exception e) {
            log.error(
                    "Initiation of the VehicleInfo of "
                            + param.vin
                            + param.originInfo
                            + " failed: {}",
                    e.getMessage());
        }
        return null;
    }

    @Data
    static class InitParam {
        String address;
        String vin;
        String originInfo;

        @JsonCreator
        public InitParam(@JsonProperty("address") String address,
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
    public TransactionReceipt updateVehicleMaintenance(@RequestBody UpdateParam param) {
        try {
            VehicleQuery vehiclequery = load(param.address);
            TransactionReceipt state = vehiclequery.updateVehicleMaintenance(param.vin, param.remarks, param.info).send();
            return state;
        } catch (Exception e) {
            log.error(
                    "Update of the Maintenance record of " + param.vin + " failed: {}",
                    e.getMessage());
        }
        return null;
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
    public TransactionReceipt addApproved_web(@RequestBody AddApprovedParam param) {
        VehicleQuery vehiclequery = load(param.address);
        try {
            TransactionReceipt state = vehiclequery.addApprovedMaintenanceShop(param.approved).send();
            return state;
        }catch (Exception e)
        {

        }
        JSONObject result = new JSONObject();
        result.put("msg", "The Addition of Approved MaintenanceShopls address failed.");
        return null;
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
                 //log.info("getVehicleTotalInfo failed+" + index + numsofrecords);
                JSONObject temp = new JSONObject();
                Tuple4<String, String, String, BigInteger> response =
                        vehiclequery.getTotalInfo(VIN, index).send();
                temp.put("MaintenanceInfo", response.getValue1());
                temp.put("remark", response.getValue2());
                temp.put("MaintenanceShopAddress", response.getValue3());
                temp.put("TimeStamp", response.getValue4());
                jsona.add(temp);
                //log.info("getVehicleTotalInfo failed+" + temp);
            }
            result.put("Records", jsona);
            return result.toString();
        } catch (Exception e) {
            log.error("getVehicleTotalInfo failed: {}", e.getMessage());
        }
        return null;
    }
}
