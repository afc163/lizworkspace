var sequencer, strain_name_1, strain_name_2, subtype, isolation_city, short_isolation_city, isolation_year, segment_name;

function obtain_final_name() {
    // 拼合上传时的序列字符串
    sequencer = $("input[name='sequencer']").val();
	strain_name_1 = $("input[name='strain_name_1']").val();
	subtype = $("input[name='subtype']").val();
	strain_name_2 = $("input[name='strain_name_2']").val();
	isolation_city = $("input[name='isolation_city']").val();
	isolation_year = $("input[name='isolation_year']").val();
	segment_name = $("select[name='segment_name']").val();
    short_isolation_city = '';
    
    var i, j;
    for (i=0; i<isolation_city.length; i++) {
        j = isolation_city.charAt(i);
        if (j >='A' && j <= 'Z') {
            short_isolation_city += j;
        }
    }

	$("input[name='final_name']").val('>lb|'+sequencer+'|/'+strain_name_1+'/'+segment_name[8]+segment_name.substring(segment_name.indexOf('('), segment_name.length-5)+'/'+subtype+'/'+short_isolation_city+'/'+strain_name_2+'/'+isolation_year.substring(2, 4)+', Influenza A virus (A/Quail/'+isolation_city+'/'+strain_name_2+'/'+isolation_year+'('+subtype+')) '+segment_name+', complete sequence');
}


$(document).ready(function(){
	obtain_final_name();
	
	$("input[name='sequencer'], input[name='strain_name_1'], input[name='strain_name_2'], input[name='subtype'], input[name='isolation_city'], input[name='isolation_year'], select[name='segment_name']").change(function() {
        obtain_final_name();
    }); 
	
	setTimeout(function(){
                  $("h4#msg").hide("slow");
               }, 8000);
});
