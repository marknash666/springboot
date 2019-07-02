package org.fisco.bcos;

import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.temp.VehicleQuery;
import org.fisco.bcos.VehicleMaintenanceController;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VehicleQueryTest extends BaseTest {

    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;
    static VehicleMaintenanceController VMC;
    static VehicleQuery vehiclequery;
    static BigInteger time;

    //deploy contract VehicleQuery and check the facturingInfo
    @Before
    @Test
    public void deployAndCallVehicle() throws Exception {
        VMC=new VehicleMaintenanceController(web3j,credentials);
        vehiclequery=VMC.deploy();
        System.out.println(vehiclequery.getContractAddress());
        vehiclequery.ManufactureInit("123456", "large and luxurious").send();
        vehiclequery.updateVehicleMaintenance("123456", "the vehicle is ok", "replace the bumper").send();
        vehiclequery.updateVehicleMaintenance("123456", "tries need to be checked regularly", "repalce the tires").send();
        vehiclequery.updateVehicleMaintenance("123456", "nothing wrong", "spray red paint").send();
        time=vehiclequery.getTimeStamp("123456",new BigInteger("1")).send();
        String result=vehiclequery.getVehicleManufacturingInfo("123456").send();
        assertEquals("large and luxurious",result);
    }

    //test the Vehicle Info
    @Test
    public void testInfo() throws Exception {
        System.out.println(vehiclequery);
        String Info=vehiclequery.getInfo("123456",new BigInteger("0")).send();
        assertEquals("replace the bumper",Info);
    }


    @Test
    public void testNumOfRecord() throws Exception {
        BigInteger nums=vehiclequery.getNumsOfRecords("123456").send();
        assertTrue((new BigInteger("3").compareTo(nums))==0);
    }

    @Test
    public void testRemark() throws Exception {
        String s="nothing wrong";
        String remark=vehiclequery.getRemark("123456",new BigInteger("2")).send();
        assertEquals(s,remark);
    }

    @Test
    public void testTimeStamp() throws Exception {
        BigInteger timestamp=vehiclequery.getTimeStamp("123456",new BigInteger("1")).send();
        String remark=vehiclequery.getRemark("123456",new BigInteger("2")).send();
        assertTrue(time.compareTo(timestamp)==0);
    }


}
