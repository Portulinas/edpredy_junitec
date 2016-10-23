/*Dados que devem ser definidos pelo java: */
g_x = [[],[],[],[]];
g_y = [[],[],[],[]];
g_shapes = [[],[],[]];

g_shapes[0] = [
  {x0: 0.3, x1: 0.6}
];



/*Auxiliar para gerar dados de teste:*/
function randn_bm() {
  var u = 1 - Math.random(); // Subtraction to flip [0, 1) to (0, 1].
  var v = 1 - Math.random();
  return Math.sqrt( -2.0 * Math.log( u ) ) * Math.cos( 2.0 * Math.PI * v );
}
/////



/*Auxiliar gera data aleatoria: */
function zeroPad(num, places) {
  var zero = places - num.toString().length + 1;
  return Array(+(zero > 0 && zero)).join("0") + num;
}

for (i=0; i<288; i++) {
  g_x[0].push('' +zeroPad(Math.floor((i*5)/60),2) + ":" + zeroPad((i*5)%60,2));
  g_y[0].push(25 + 2.4*Math.sin(i/40 - 20 - randn_bm()/10) + randn_bm()/1.5);
}
///


/*
function readCVSFile(file){
	$.ajaxSetup({async:false});
	$.get(file,function(data,status){
		var lines = data.split('\n');
		for(var i = 0; i < lines.length - 1; i++){
			var line_data = lines[i].split(',');
			console.log(parseFloat(line_data[1]));
			g_y[0].push(parseFloat(line_data[1]));
		}
		console.log(status);
	});
}
//readCVSFile("js/data/2016-09-23_Ext.csv");
//console.log("IM HERE!!!!!!!!!!!!!!!!!!!!!!!!!!");
*/


/* O mesmo para os outros graficos: */
g_x[1] = g_x[0];
//g_y[1] = g_y[0];
g_shapes[1] = g_shapes[0];
g_x[2] = g_x[0];
//g_y[2] = g_y[0];
g_shapes[2] = g_shapes[0];
