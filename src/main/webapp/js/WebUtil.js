function fetchData(){
var parameterValue= document.getElementById("paramName").value;

console.log("Entered parameter value is : "+parameterValue);
var url = "https://otmgtm-test-a531163.otm.em2.oraclecloud.com/logisticsRestApi/resources-int/v2/orderBases/FLS/EUR.";
 
 console.log("After appending the parameter to the url ");
 url = url+ parameterValue;
 console.log(url);
}