package org.fisco.bcos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.fisco.bcos.temp.VehicleOwnership;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VMControlerTest extends BaseTest{
    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;
    static VehicleMaintenanceController VMC;
    static VehicleOwnership vehiclequery;
    static String address;
    static VehicleMaintenanceController.InitParam initparam;

    @Data
    static class KeyParam {
        String userKey;

        @JsonCreator
        public KeyParam(@JsonProperty("userKey") String userKey) {
            this.userKey = userKey;
        }
    }
    @Before
    @Test
    public void testDeploy() throws Exception {
        VMC=new VehicleMaintenanceController(web3j,credentials);
        VMC._changeUser("3bed914595c159cbce70ec5fb6aff3d6797e0c5ee5a7a9224a21cae8932d84a4");
        vehiclequery=VMC.deploy();
        address=vehiclequery.getContractAddress();
        assertTrue(vehiclequery!=null);
    }

    @Test
    public void testLoad() throws Exception {
        VehicleOwnership VOS=VMC.load(address);
        assertEquals(address,VOS.getContractAddress());
    }

    @Test
    public void testCredentials() throws Exception {
        Credentials cred=VMC.getCredentials();
        assertTrue(cred!=null);
    }
    @Test
    public void testMFI() throws Exception {
        initparam = new VehicleMaintenanceController.InitParam(address,"123456","hhh");
        String s=VMC.manufactureInit(initparam);
        assertEquals("{\"status\":true}",s);
    }
    @Data
    static class UpdateParam {
        String address;
        String vin;
        String remarks;
        String info;
    }
    @Test
    public void testupdateVM() throws Exception {
        initparam = new VehicleMaintenanceController.InitParam(address,"123456","hhh");
        String s=VMC.manufactureInit(initparam);
        assertEquals("{\"status\":true}",s);
    }

}
