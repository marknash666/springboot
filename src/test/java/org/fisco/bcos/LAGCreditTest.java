package org.fisco.bcos;

import static org.junit.Assert.assertTrue;

import org.fisco.bcos.temp.LAGCredit;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LAGCreditTest extends BaseTest {

    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;

    @Test
    public void deployAndCallHelloWorld() throws Exception {
        // deploy contract
        LAGCreditController lagcreditcontrol = new LAGCreditController(web3j, credentials);
        LAGCredit sad = lagcreditcontrol.deploy();
        LAGCredit ewe = lagcreditcontrol.load(sad.getContractAddress());
        long result = lagcreditcontrol.getTotalSupply(ewe.getContractAddress());
        long origin = 100000;
        assertTrue(origin == result);
    }
}
