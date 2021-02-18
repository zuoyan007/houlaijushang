

export const projectKit =   {
	fmtStatusClass:(projectStatus,delayDayCount)=>{
		if(projectStatus==20){
			if(delayDayCount>0){
				return 'bg-red'
			}else{
				return 'bg-green'
			}
		}else if(projectStatus==30){
			return 'bg-blue'
		}
		return 'bg-grey'
	},
	fmtCheckStatusClass:(checkStatus)=>{
		if(checkStatus==20){
			return 'bg-orange'
		}else if(checkStatus==30){
			return 'bg-green'
		}
		return 'bg-grey'
	},
	formatStatusLabel:(projectStatus,delayDayCount)=>{
		// var projectStatusLabel = $dictKit.getLabel('pm_project_status',data.projectStatus);
		// {{data.delayDayCount>0?'延期'+data.delayDayCount+'天':$dictKit.getLabel('pm_project_status',data.projectStatus)}}
		
		// if(projectStatus==10){
		// 	return projectStatusLabel;
		// }
		// if(projectStatus==){
			
		// }
		// return 'bg-grey'
	}
};

