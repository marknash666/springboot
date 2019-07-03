package org.fisco.bcos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.math.BigInteger;
import lombok.Data;
import org.fisco.bcos.temp.VehicleOwnership;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tuples.generated.Tuple4;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class VehicleOwnershipTest extends BaseTest {

    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;
    static VehicleMaintenanceController VMC;
    static VehicleOwnership vehiclequery;
    static BigInteger time;

    @Data
    static class KeyParam {
        String userKey;

        @JsonCreator
        public KeyParam(@JsonProperty("userKey") String userKey) {
            this.userKey = userKey;
        }
    }

    // deploy contract VehicleOwnership and check the facturingInfo
    @Before
    @Test
    public void deployAndCallVehicle() throws Exception {

        VMC = new VehicleMaintenanceController(web3j, credentials);
        VMC._changeUser("3bed914595c159cbce70ec5fb6aff3d6797e0c5ee5a7a9224a21cae8932d84a4");
        vehiclequery = VMC.deploy();
        System.out.println(vehiclequery.getContractAddress());
        vehiclequery.ManufactureInit("123456", "large and luxurious").send();
        vehiclequery
                .updateVehicleMaintenance("123456", "the vehicle is ok", "replace the bumper")
                .send();
        vehiclequery
                .updateVehicleMaintenance(
                        "123456", "tries need to be checked regularly", "repalce the tires")
                .send();
        vehiclequery.updateVehicleMaintenance("123456", "nothing wrong", "spray red paint").send();
        time = vehiclequery.getTimeStamp("123456", new BigInteger("1")).send();
        String result = vehiclequery.getVehicleManufacturingInfo("123456").send();
        assertEquals("large and luxurious", result);
    }

    // test the Vehicle Info
    @Test
    public void testInfo() throws Exception {
        System.out.println(vehiclequery);
        String Info = vehiclequery.getInfo("123456", new BigInteger("0")).send();
        assertEquals("replace the bumper", Info);
    }

    @Test
    public void testNumOfRecord() throws Exception {
        BigInteger nums = vehiclequery.getNumsOfRecords("123456").send();
        assertTrue((new BigInteger("3").compareTo(nums)) == 0);
    }

    @Test
    public void testRemark() throws Exception {
        String s = "nothing wrong";
        String remark = vehiclequery.getRemark("123456", new BigInteger("2")).send();
        assertEquals(s, remark);
    }

    @Test
    public void testTimeStamp() throws Exception {
        BigInteger timestamp = vehiclequery.getTimeStamp("123456", new BigInteger("1")).send();
        String remark = vehiclequery.getRemark("123456", new BigInteger("2")).send();
        assertTrue(time.compareTo(timestamp) == 0);
    }

    @Test
    public void testExistence() throws Exception {
        String VIN = "123456";
        boolean exists = vehiclequery.getExistence(VIN).send();
        assertTrue(exists);

        VIN = "345";
        exists = vehiclequery.getExistence(VIN).send();
        assertFalse(exists);
    }

    @Test
    public void testAddressAndOwnerOf() throws Exception {
        String VIN = "123456";
        BigInteger index = new BigInteger("0");

        assertEquals(vehiclequery.getAddress(VIN, index).send(), vehiclequery.ownerOf(VIN).send());
    }

    @Test
    public void testTotalInfo() throws Exception {
        String VIN = "123456";
        BigInteger index = new BigInteger("0");
        Tuple4<String, String, String, BigInteger> a = vehiclequery.getTotalInfo(VIN, index).send();
        assertEquals(a.getValue1(), "replace the bumper"); // info
        assertEquals(a.getValue2(), "the vehicle is ok"); // remark
        String address = vehiclequery.ownerOf(VIN).send();
        assertEquals(a.getValue3(), address); // shop address
        time = vehiclequery.getTimeStamp("123456", new BigInteger("0")).send();
        assertEquals(a.getValue4(), time); // time
    }

    @Test
    public void testBalanceOf() throws Exception {
        String VIN = "123456";
        String address = vehiclequery.ownerOf(VIN).send();
        assertEquals(vehiclequery.balanceOf(address).send(), new BigInteger("1"));
    }

    @Test
    public void testTransferTo() throws Exception {
        String VIN = "123456";
        String address = vehiclequery.ownerOf(VIN).send();

        String toaddress = "123";
        vehiclequery.transfer(toaddress, VIN).send();

        assertEquals(vehiclequery.balanceOf(address).send(), new BigInteger("0"));
        assertEquals(
                vehiclequery.ownerOf(VIN).send(), "0x0000000000000000000000000000000000000123");
    }
}
