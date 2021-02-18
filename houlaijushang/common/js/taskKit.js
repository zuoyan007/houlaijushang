

export const taskKit =   {
	fmtStatusClass:(checkStatus)=>{
		if(checkStatus==10){
			return 'bg-red'
		}else if(checkStatus==20){
			return 'bg-yellow'
		}else if(checkStatus==30){
				return 'bg-yellow'
		}else if(checkStatus==40){
				return 'bg-green'
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
	}
	
};

