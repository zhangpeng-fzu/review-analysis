$(document).ready(function(){
    //$(".href").click(function(){
        //var start = $("#time_start").val();
        //var end = $("#time_end").val();
        //start = timeToStamp(start);
        //end = timeToStamp(end);
        //alert(start);
        //alert(end);
    //    var url = $(this).data("href");
    //    window.open(url);
    //})
});
function timeToStamp(time){
    if(time === undefined || time === ""){
        return 0;
    }
    var commonTime =  new Date(time.replace("时",":00:00"));
    return commonTime.getTime();
}