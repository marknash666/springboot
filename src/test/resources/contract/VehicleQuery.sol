pragma solidity >=0.4.22 <0.6.0;

import "./VehicleUpdate.sol";

contract VehicleQuery is VehicleUpdate {
    
     //获取汽车维护信息
    function getVehicleManufacturingInfo(string memory VIN) public view returns (string memory) {
        Vehicle memory targetcar = VINtoVehicle[VIN];
        require( VINtoVehicle[VIN].initialized == 1,"This VIN is currently invalid,please check the number carefully");
        return targetcar.ManufacturingInfo;
    }
    
    //index为维修记录的下标,获取维修保养注信息
    function getInfo(string memory VIN, uint index) public view returns (string memory) {
        Vehicle memory targetcar = VINtoVehicle[VIN];
        require( VINtoVehicle[VIN].initialized == 1,"This VIN is currently invalid,please check the number carefully");
        return targetcar.records[index]._info;
    }
     //index为维修记录的下标,获取维修点信息
    function getAddress(string memory VIN, uint index) public view returns (address) {
        Vehicle memory targetcar = VINtoVehicle[VIN];
        require( VINtoVehicle[VIN].initialized == 1,"This VIN is currently invalid,please check the number carefully");
        return targetcar.records[index]._MaintenanceShop;
    }
     //获取汽车维修记录条数
    function getNumsOfRecords(string memory VIN) public view returns (uint) {
        Vehicle memory targetcar = VINtoVehicle[VIN];
        require( VINtoVehicle[VIN].initialized == 1,"This VIN is currently invalid,please check the number carefully");
        return targetcar.records.length;
    }
    
    //index为维修记录的下标,获取维修备注信息
     function getRemark(string memory VIN, uint index) public view returns (string memory) {
        Vehicle memory targetcar = VINtoVehicle[VIN];
        require( VINtoVehicle[VIN].initialized == 1,"This VIN is currently invalid,please check the number carefully");
        return targetcar.records[index]._info;
    }
    
    
    
}

