package org.fisco.bcos.temp;

import io.reactivex.Flowable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
public class LAGCredit extends Contract {
    private static final String BINARY = "60806040526040805190810160405280600481526020017f4c414743000000000000000000000000000000000000000000000000000000008152506000908051906020019061004f9291906101a8565b506040805190810160405280600381526020017f4c414700000000000000000000000000000000000000000000000000000000008152506001908051906020019061009b9291906101a8565b5060016003553480156100ad57600080fd5b50604051610c24380380610c2483398101806040528101908080519060200190929190805182019291906020018051820192919050505082600281905550600254600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555081600090805190602001906101479291906101a8565b50806001908051906020019061015e9291906101a8565b5033600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050505061024d565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106101e957805160ff1916838001178555610217565b82800160010185558215610217579182015b828111156102165782518255916020019190600101906101fb565b5b5090506102249190610228565b5090565b61024a91905b8082111561024657600081600090555060010161022e565b5090565b90565b6109c88061025c6000396000f30060806040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630bae32881461009357806340753a76146100be578063442890d5146100eb578063564f1a291461014257806370a082311461016d578063a9059cbb146101c4578063aae5620d14610211578063c4e41b221461023e575b600080fd5b34801561009f57600080fd5b506100a8610269565b6040518082815260200191505060405180910390f35b3480156100ca57600080fd5b506100e960048036038101908080359060200190929190505050610273565b005b3480156100f757600080fd5b5061010061032c565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561014e57600080fd5b50610157610356565b6040518082815260200191505060405180910390f35b34801561017957600080fd5b506101ae600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061039d565b6040518082815260200191505060405180910390f35b3480156101d057600080fd5b5061020f600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506103e6565b005b34801561021d57600080fd5b5061023c600480360381019080803590602001909291905050506103f5565b005b34801561024a57600080fd5b5061025361045d565b6040518082815260200191505060405180910390f35b6000600354905090565b33600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156102d057600080fd5b600254820160028190555081600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055505050565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b6000600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905090565b6000600560008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b6103f1338383610467565b5050565b33600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561045257600080fd5b816003819055505050565b6000600254905090565b6000808373ffffffffffffffffffffffffffffffffffffffff161415151561051d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602a8152602001807f54686520616464726573732073686f756c6420626520746865206275726e696e81526020017f672061646472657373210000000000000000000000000000000000000000000081525060400191505060405180910390fd5b81600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101515156105d4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f4e6f20656e6f75676820737570706c792e00000000000000000000000000000081525060200191505060405180910390fd5b600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054011115156106f1576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260248152602001807f4578706563746564206120706f7369746976652076616c7565206f662073757081526020017f706c792e0000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054019050600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156107d257600354820291505b81600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055507f5d439cf3a1f6215b41908e5b0b300f39679a3ff1cc010691c1d4ec4d0e2a4ebb848484604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828152602001935050505060405180910390a180600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054600560008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540114151561099657fe5b505050505600a165627a7a72305820a8a24d542112b5a1c298107bfae9814ea711b976d0c7c817a206a0aa300f97c80029";

    public static final String FUNC_GETSALE = "getSale";

    public static final String FUNC_ADDSUPPLY = "addSupply";

    public static final String FUNC_GETCONTRACTOWNER = "getContractOwner";

    public static final String FUNC_BALANCEOFCUR_USER = "balanceOfCur_user";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_SETSALE = "setSale";

    public static final String FUNC_GETTOTALSUPPLY = "getTotalSupply";

    public static final Event TRANSFEREVENT_EVENT = new Event("transferEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected LAGCredit(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LAGCredit(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LAGCredit(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LAGCredit(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> getSale() {
        final Function function = new Function(FUNC_GETSALE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addSupply(BigInteger amountofSupply) {
        final Function function = new Function(
                FUNC_ADDSUPPLY, 
                Arrays.<Type>asList(new Uint256(amountofSupply)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addSupply(BigInteger amountofSupply, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDSUPPLY,
                Arrays.<Type>asList(new Uint256(amountofSupply)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<String> getContractOwner() {
        final Function function = new Function(FUNC_GETCONTRACTOWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> balanceOfCur_user() {
        final Function function = new Function(FUNC_BALANCEOFCUR_USER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new Address(_owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(_to),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transfer(String _to, BigInteger _value, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(_to),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> setSale(BigInteger new_sale) {
        final Function function = new Function(
                FUNC_SETSALE,
                Arrays.<Type>asList(new Uint256(new_sale)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setSale(BigInteger new_sale, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETSALE,
                Arrays.<Type>asList(new Uint256(new_sale)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<BigInteger> getTotalSupply() {
        final Function function = new Function(FUNC_GETTOTALSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<TransferEventEventResponse> getTransferEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFEREVENT_EVENT, transactionReceipt);
        ArrayList<TransferEventEventResponse> responses = new ArrayList<TransferEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventEventResponse typedResponse = new TransferEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventEventResponse> transferEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventEventResponse>() {
            @Override
            public TransferEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFEREVENT_EVENT, log);
                TransferEventEventResponse typedResponse = new TransferEventEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventEventResponse> transferEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFEREVENT_EVENT));
        return transferEventEventFlowable(filter);
    }

    @Deprecated
    public static LAGCredit load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LAGCredit(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LAGCredit load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LAGCredit(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LAGCredit load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new LAGCredit(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LAGCredit load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LAGCredit(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LAGCredit> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialSupply, String creditName, String creditSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creditName),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creditSymbol)));
        return deployRemoteCall(LAGCredit.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<LAGCredit> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialSupply, String creditName, String creditSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creditName),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creditSymbol)));
        return deployRemoteCall(LAGCredit.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LAGCredit> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String creditName, String creditSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creditName),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creditSymbol)));
        return deployRemoteCall(LAGCredit.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LAGCredit> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String creditName, String creditSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creditName), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creditSymbol)));
        return deployRemoteCall(LAGCredit.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class TransferEventEventResponse {
        public Log log;

        public String from;

        public String to;

        public BigInteger value;
    }
}
