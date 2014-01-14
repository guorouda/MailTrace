function getCurrentDateTime(){

   var date = new Date();

   var yyyy = date.getFullYear();
   var m = date.getMonth() + 1;
   var mm = (m < 10) ? '0' + m : m;
   var d  = date.getDate();
   var dd = (d < 10) ? '0' + d : d;

   var h = date.getHours();
   var hh = (h < 10) ? '0' + h : h;
   var n = date.getMinutes();
   var nn = (n < 10) ? '0' + n : n;
   var s  = date.getSeconds();
   var ss = (s < 10) ? '0' + s : s;

   var dt=yyyy + "-" + mm + "-" + dd + "-" + " " + hh + ":" + nn + ":" + ss ;

   alert(dt);
   return dt;
}