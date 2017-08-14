$(document).ready(function(){
	$(".datepicker").each(function(){
		$(this).focus(function(){ 
	    	WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'});
	    });
	    
	    var value = $(this).val();
			
		var pattern = /(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})/;
		
		if(pattern.test(value)){
			var arr = value.match(pattern);
			$(this).val(arr[1] + "-" + arr[2] + "-" + arr[3] + " " + arr[4] + ":" + arr[5]);
		}		
	});
	
	$(".datepicker2").each(function(){
		$(this).focus(function(){ 
	    	WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'});
	    });
	    
	    var value = $(this).val();
			
		var pattern = /(\d{4})-(\d{2})-(\d{2})/;
		
		if(pattern.test(value)){
			var arr = value.match(pattern);
			$(this).val(arr[1] + "-" + arr[2] + "-" + arr[3]);
		}		
	});
});