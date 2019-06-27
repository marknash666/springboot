pragma solidity >=0.4.22 <0.6.0;



contract VehicleUpdate {
    
    address[] internal ApprovedMaintenanceShop = new address[](1);//授权维修点地址集合
    address internal Administrator;//合约部署者，管理员
    mapping (string => Vehicle) internal  VINtoVehicle;//以车架号对应车辆
    
    constructor () public{
        ApprovedMaintenanceShop.push(msg.sender);
    }
    
    struct MaintenanceRecord {
        string _remarks;//备注
        string _info;//维修保养的具体信息
        address _MaintenanceShop;//记录维修地点
        uint _time;
    }

    struct  Vehicle {
        string ManufacturingInfo;//汽车出厂信息
        MaintenanceRecord[] records;//车辆所拥有的维修保养信息
        uint8 initialized;
    }
    
    //只有允许的维修点才能执行的修饰函数
    modifier onlyMaintenanceShop(address){
        uint label=0;
        for(uint cnt;cnt<ApprovedMaintenanceShop.length;cnt++)
        {
            if(msg.sender == ApprovedMaintenanceShop[cnt])
            label++;
        }
         require(label == 1,"This function can only be exerted by the MaintenanceShop");
        _;
    }
    
    //只有管理员才能执行的修饰函数
     modifier onlyAdministrator(address){
         require(msg.sender == Administrator,"This function can only be exerted by the Administrator");
        _;
    }
    
    function addApprovedMaintenanceShop(address approvedAddress) public onlyAdministrator(msg.sender){
        ApprovedMaintenanceShop.push(approvedAddress);
    }
    
     //获取汽车维护信息

     //出厂信息设置
    function ManufactureInit(string memory VIN, string memory originInfo) public onlyMaintenanceShop(msg.sender) {
        require( VINtoVehicle[VIN].initialized == 0,"The ManufacturingInfo is already initialized");
        VINtoVehicle[VIN].ManufacturingInfo =(originInfo);
        VINtoVehicle[VIN].initialized = 1;
    }
    
    //更新汽车维护信息
    function updateVehicleMaintenance (string memory VIN,string memory remarks,string memory info) public onlyMaintenanceShop(msg.sender){
         Vehicle storage targetcar = VINtoVehicle[VIN];
         require( VINtoVehicle[VIN].initialized == 1,"This VIN is currently invalid,please check the number carefully");
         MaintenanceRecord memory newRecord;
         newRecord._remarks=remarks;
         newRecord._info=info;
         newRecord._MaintenanceShop = msg.sender;
         newRecord._time =now; 
         targetcar.records.push(newRecord);
    }
}

