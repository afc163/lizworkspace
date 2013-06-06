$(document).ready(function(){
	$("div[id^='content']").hide();

	$("h3[id^='r']").click(function(){
		var filename=$(this).attr("id");
		cont="#content"+filename.substring(1,filename.length);
		//content="div[id='content"+comment.substring(3,comment.length)+"']";
		if(!$(cont).attr("visible")){
			$(cont).show("slow");
		}
		if(!$(cont).attr("hidden")){
			$(cont).hide();
		}
	}); 
	///var keyword=$("span[id='keyword']").html()
	///$("span[id='filename']>").bind("myEvent", function(){
	///	obj=$(this).html()
	///	obj.replace(keyword, '<span id="showkey">'+keyword+'</span>');
	///	$(this).html("obj");
	///});
	///$("span[id='filename']>").trigger("myEvent");
//	$("h4[id^='comments']").toggle(function()
//				{
//					$("div[id='comment']").show("slow");/*animate(
//					{
//						height:"show",
//						opacity:"show"
//					}, "slow");*/
//				},
//				function()
//				{
//					$("div[id='comment']").hide("slow");/*animate(
//					{
//						height:"hide",
//						opacity:"hide"
//					}, "slow");*/
//				});

	//$("#comment").find("ol").hide().end().find("#comments").click(function()
	//												{
	//													var answer = $(this).next();
	//													if (answer.is('.visible'))
	//													{
	//														answer.slideUp();
	//													}
	//													else
	//													{
	//														answer.slideDown();
	//													}
	//												}); 
});
