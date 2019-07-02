package org.fisco.bcos.solidity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class VehicleUpdate extends Contract {
    private static final String BINARY = "608060405260016040519080825280602002602001820160405280156100345781602001602082028038833980820191505090505b506000908051906020019061004a9291906100c3565b5034801561005757600080fd5b5060003390806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050610190565b82805482825590600052602060002090810192821561013c579160200282015b8281111561013b5782518260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550916020019190600101906100e3565b5b509050610149919061014d565b5090565b61018d91905b8082111561018957600081816101000a81549073ffffffffffffffffffffffffffffffffffffffff021916905550600101610153565b5090565b90565b610c748061019f6000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063238cecf01461005c5780632e06e48214610151578063d3e8acfa14610200575b600080fd5b34801561006857600080fd5b5061014f600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610243565b005b34801561015d57600080fd5b506101fe600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610629565b005b34801561020c57600080fd5b50610241600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061098e565b005b600061024d610ae4565b33600080600091505b6000805490508110156102e65760008181548110151561027257fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156102d95781806001019250505b8080600101915050610256565b600182141515610384576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260388152602001807f546869732066756e6374696f6e2063616e206f6e6c792062652065786572746581526020017f6420627920746865204d61696e74656e616e636553686f70000000000000000081525060400191505060405180910390fd5b6002886040518082805190602001908083835b6020831015156103bc5780518252602082019150602081019050602083039250610397565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020945060016002896040518082805190602001908083835b60208310151561042b5780518252602082019150602081019050602083039250610406565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060020160009054906101000a900460ff1660ff1614151561050d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252603f8152602001807f546869732056494e2069732063757272656e746c7920696e76616c69642c706c81526020017f6561736520636865636b20746865206e756d626572206361726566756c6c790081525060400191505060405180910390fd5b86846000018190525085846020018190525033846040019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250504284606001818152505084600101849080600181540180825580915050906001820390600052602060002090600402016000909192909190915060008201518160000190805190602001906105ad929190610b23565b5060208201518160010190805190602001906105ca929190610b23565b5060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550606082015181600301555050505050505050505050565b33600080600091505b6000805490508110156106c25760008181548110151561064e57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156106b55781806001019250505b8080600101915050610632565b600182141515610760576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260388152602001807f546869732066756e6374696f6e2063616e206f6e6c792062652065786572746581526020017f6420627920746865204d61696e74656e616e636553686f70000000000000000081525060400191505060405180910390fd5b60006002866040518082805190602001908083835b60208310151561079a5780518252602082019150602081019050602083039250610775565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060020160009054906101000a900460ff1660ff1614151561087c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602c8152602001807f546865204d616e75666163747572696e67496e666f20697320616c726561647981526020017f20696e697469616c697a6564000000000000000000000000000000000000000081525060400191505060405180910390fd5b836002866040518082805190602001908083835b6020831015156108b55780518252602082019150602081019050602083039250610890565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060000190805190602001906108fe929190610ba3565b5060016002866040518082805190602001908083835b6020831015156109395780518252602082019150602081019050602083039250610914565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060020160006101000a81548160ff021916908360ff1602179055505050505050565b33600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610a7a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260368152602001807f546869732066756e6374696f6e2063616e206f6e6c792062652065786572746581526020017f64206279207468652041646d696e6973747261746f720000000000000000000081525060400191505060405180910390fd5b60008290806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050565b6080604051908101604052806060815260200160608152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b6457805160ff1916838001178555610b92565b82800160010185558215610b92579182015b82811115610b91578251825591602001919060010190610b76565b5b509050610b9f9190610c23565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610be457805160ff1916838001178555610c12565b82800160010185558215610c12579182015b82811115610c11578251825591602001919060010190610bf6565b5b509050610c1f9190610c23565b5090565b610c4591905b80821115610c41576000816000905550600101610c29565b5090565b905600a165627a7a72305820a2e1c449461d6e1c5fe2f0225c8b19c5421e70908fe45522c15e29f38644c5c10029";

    public static final String FUNC_UPDATEVEHICLEMAINTENANCE = "updateVehicleMaintenance";

    public static final String FUNC_MANUFACTUREINIT = "ManufactureInit";

    public static final String FUNC_ADDAPPROVEDMAINTENANCESHOP = "addApprovedMaintenanceShop";

    @Deprecated
    protected VehicleUpdate(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VehicleUpdate(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VehicleUpdate(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VehicleUpdate(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> updateVehicleMaintenance(String VIN, String remarks, String info) {
        final Function function = new Function(
                FUNC_UPDATEVEHICLEMAINTENANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(VIN), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(remarks), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(info)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateVehicleMaintenance(String VIN, String remarks, String info, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATEVEHICLEMAINTENANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(VIN), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(remarks), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(info)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> ManufactureInit(String VIN, String originInfo) {
        final Function function = new Function(
                FUNC_MANUFACTUREINIT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(VIN), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(originInfo)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void ManufactureInit(String VIN, String originInfo, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_MANUFACTUREINIT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(VIN), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(originInfo)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> addApprovedMaintenanceShop(String approvedAddress) {
        final Function function = new Function(
                FUNC_ADDAPPROVEDMAINTENANCESHOP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(approvedAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addApprovedMaintenanceShop(String approvedAddress, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDAPPROVEDMAINTENANCESHOP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(approvedAddress)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    @Deprecated
    public static VehicleUpdate load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VehicleUpdate(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VehicleUpdate load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VehicleUpdate(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VehicleUpdate load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VehicleUpdate(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VehicleUpdate load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VehicleUpdate(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<VehicleUpdate> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VehicleUpdate.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<VehicleUpdate> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VehicleUpdate.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VehicleUpdate> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VehicleUpdate.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VehicleUpdate> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VehicleUpdate.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
