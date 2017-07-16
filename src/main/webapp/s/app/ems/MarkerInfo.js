MarkerInfo = function (markerBusinessDto) {
    if(markerBusinessDto.markerDto){
        this.id = markerBusinessDto.markerDto.id;
        this.markerObjectType = markerBusinessDto.markerDto.markerObjectType;
        this.ownerComp = markerBusinessDto.markerDto.ownerComp;
        this.area = markerBusinessDto.markerDto.area;
        this.antiCorrodedNo = markerBusinessDto.markerDto.antiCorrodedNo;
        this.projectName = markerBusinessDto.markerDto.projectName;
        this.projectNo = markerBusinessDto.markerDto.projectNo;
        this.line = markerBusinessDto.markerDto.line;
        this.jobNo = markerBusinessDto.markerDto.jobNo;
        this.jobContent = markerBusinessDto.markerDto.jobContent;
        this.pressLevel = markerBusinessDto.markerDto.pressLevel;
        this.pipeMaterial = markerBusinessDto.markerDto.pipeMaterial;
        this.pipeDiameter = markerBusinessDto.markerDto.pipeDiameter;
        this.depth = markerBusinessDto.markerDto.depth;
        this.antiCorrodedType = markerBusinessDto.markerDto.antiCorrodedType;
        this.designThickness = markerBusinessDto.markerDto.designThickness;
        this.road = markerBusinessDto.markerDto.road;
        this.testPile = markerBusinessDto.markerDto.testPile;
        this.devCode = markerBusinessDto.markerDto.devCode;
        this.testPileBuildComp = markerBusinessDto.markerDto.testPileBuildComp;
        this.constructTime = markerBusinessDto.markerDto.constructTime;
        this.x_cord = markerBusinessDto.markerDto.x_cord;
        this.y_cord = markerBusinessDto.markerDto.y_cord;
        this.memo = markerBusinessDto.markerDto.memo;
        this.markerId = markerBusinessDto.markerDto.markerId;
        this.markerType = markerBusinessDto.markerDto.markerType;
        this.markerDepth = markerBusinessDto.markerDto.markerDepth;
        this.department = markerBusinessDto.markerDto.department;
        this.creator = markerBusinessDto.markerDto.creator;
        this.createTime = markerBusinessDto.markerDto.createTime;
        this.longitude = markerBusinessDto.markerDto.longitude;
        this.latitude = markerBusinessDto.markerDto.latitude;
        this.recordType = markerBusinessDto.markerDto.recordType;
        this.companyId = markerBusinessDto.markerDto.companyId;
        this.newLineNo = markerBusinessDto.markerDto.newLineNo;
        this.newPipeMaterial = markerBusinessDto.markerDto.newPipeMaterial;
        this.newPipeDiameter = markerBusinessDto.markerDto.newPipeDiameter;
        this.newAntiCorrodedType = markerBusinessDto.markerDto.newAntiCorrodedType;
        this.newDesignThickness = markerBusinessDto.markerDto.newDesignThickness;
        this.newConstructTime = markerBusinessDto.markerDto.newConstructTime;
    }
   if(markerBusinessDto.businessDataDto){
       this.protectivePotential = markerBusinessDto.businessDataDto.protectivePotential;
       this.openCircuitPotential = markerBusinessDto.businessDataDto.openCircuitPotential;
       this.emissionCurrent = markerBusinessDto.businessDataDto.emissionCurrent;
       this.refEleCalibration = markerBusinessDto.businessDataDto.refEleCalibration;
       this.businessMemo = markerBusinessDto.businessDataDto.memo;
       this.status = markerBusinessDto.businessDataDto.status;
       this.checker = markerBusinessDto.businessDataDto.checker;
       this.checkTime = markerBusinessDto.businessDataDto.checkTime;
   }
   if(markerBusinessDto.repairPointDto){
       this.leftThickness = markerBusinessDto.repairPointDto.leftThickness;
       this.taskNo = markerBusinessDto.repairPointDto.taskNo;
       this.reason = markerBusinessDto.repairPointDto.reason;
       this.repairMethod = markerBusinessDto.repairPointDto.repairMethod;
       this.groundType = markerBusinessDto.repairPointDto.groundType;
       this.repairTime = markerBusinessDto.repairPointDto.repairTime;
   }
};
