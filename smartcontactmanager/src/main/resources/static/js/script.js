console.log("this is script file");

const toggleSidebar =()=>{
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
		
	}else{
		
		console.log("click on sidebar")
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
		
		
	}
	
};



