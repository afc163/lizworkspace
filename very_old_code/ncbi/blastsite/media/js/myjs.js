$(document).ready(function(){
	$("div[id^='content'], div.dnacontent").hide();
    
    $("span[id^='file']").toggle(function(){
		var filename=$(this).attr("id");
		cont="#content"+filename.substring(4,filename.length);
		$(cont).show("slow");
		$(this).html('-');
		},function(){
		var filename=$(this).attr("id");
		cont="#content"+filename.substring(4,filename.length);
		$(cont).hide("slow");
		$(this).html('+');
	});
	
	$("div.items h4").toggle(function(){
		   var dna = $(this).next("div.dnacontent");
		   dna.show("slow");
	},function(){
		   var dna = $(this).next("div.dnacontent");
		   dna.hide("slow");
	}); 

});
