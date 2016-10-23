var both = false;
var availableDates = [];
var d = new Date();
for(i=2;i<=d.getDate();i++){
	availableDates.push(''+i+'-9-2016');
}
var current_file = "js/data/2016-09-"+(d.getDate())+"_Int.csv";



function loadScript(url, callback)
{
    // Adding the script tag to the head as suggested before
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = url;

    // Then bind the event to the callback function.
    // There are several events for cross browser compatibility.
    script.onreadystatechange = callback;
    script.onload = callback;

    // Fire the loading
    head.appendChild(script);
}

function readCVSFile(file){
	$.ajaxSetup({async:false});
	$.get(file,function(data){
		var lines = data.split('\n');
		for(var i = 0; i < lines.length - 1; i++){
			var line_data = lines[i].split(',');
			g_y[0][i] = parseFloat(line_data[1]);
			g_y[1][i] = parseFloat(line_data[2]);
		}
	});
}
function readBothCVSFile(file1,file2){
	$.ajaxSetup({async:false});
	$.get(file1,function(data){
		var lines = data.split('\n');
		for(var i = 0; i < lines.length - 1; i++){
			var line_data = lines[i].split(',');
			g_y[0][i] = parseFloat(line_data[1]);
			g_y[1][i] = parseFloat(line_data[2]);
		}
	});
	$.get(file2,function(data){
		var lines = data.split('\n');
		for(var i = 0; i < lines.length - 1; i++){
			var line_data = lines[i].split(',');
			g_y[2][i] = parseFloat(line_data[1]);
			g_y[3][i] = parseFloat(line_data[2]);
		}
	});
}

function changeTitle(){
	var aux = current_file.split('_');
	var aux1;
	var aux2 = aux[0].split('/');
	  
	if(aux[1]=='Ext.csv'){
		aux1 = " Exterior";
	}else{
		aux1 = " Interior";
	}
	return aux2[2] + aux1;
}

$(document).ready(function() {
  $('.collapse.in').prev('.panel-heading').addClass('active');
  $('#accordion, #bs-collapse')
    .on('show.bs.collapse', function(a) {
      $(a.target).prev('.panel-heading').addClass('active');
    })
    .on('hide.bs.collapse', function(a) {
      $(a.target).prev('.panel-heading').removeClass('active');
    });

  /*Gera os retangulos de janela aberta:*/
  loadScript('js/data.js', function() {
	g_rects = [[],[],[]];
    trace = [[],[],[],[]];
	change_chart("temp");
	change_chart("hum");
	var date = new Date();
	index_num = parseInt(((date.getHours() * 60 + (date.getMinutes()-8)) * 288) / 1440 );
	
	var hours = Math.floor( (date.getHours() * 60 + (date.getMinutes()-8)) / 60);          
    var minutes = (date.getHours() * 60 + (date.getMinutes()-8))% 60;
	
	$('#titulo').text("Às "+hours+":"+minutes+" podemos constatar que:");
	if(Math.abs(g_y[0][index_num]-g_y[1][index_num])<1 &&  Math.abs(g_y[2][index_num]-g_y[3][index_num])<10){
		$("#janela").text("Devido à semelhança de temperaturas no interior e exterior existe a possibilidade da janela estar aberta");
	}
	if(g_y[0][index_num] > 26 || g_y[0][index_num] < 20 || g_y[2][index_num] < 20 || g_y[2][index_num] > 80){
		$("#temperatura").text("A temperatura na sala encontra-se fora da zona de conforto.");
	}
	if(g_y[2][index_num] > 80){
		$("#temperatura").text("A Humidade relativa da sala é excessiva (>80%)");
	}
	
	console.log("as "+hours+":"+minutes+" estamos no index:"+index_num);
		
	
	
	/*
	readCVSFile(current_file);
    

    for (j=0; j<2; j++) {
      g_rects[j] = [];
      for (i in g_shapes[j]) {
        pt = g_shapes[j];
        g_rects[j].push({
          type: 'rect',
          // x-reference is assigned to the x-values
          xref: 'paper',
          // y-reference is assigned to the plot paper [0,1]
          yref: 'paper',
          x0: pt.x0,
          y0: 0,
          x1: pt.x1,
          y1: 1,
          fillcolor: '#a3ffa3',
          opacity: 0.4,
          line: {
              width: 0
          }
        });
      }

      /* Example points for debuging: *//*
      trace[j] = {
        x: g_x[j],
        y: g_y[j],
        mode: 'lines',
        line: {
          dash: 'lines',
          width: 4,
          color: 'rgba(238,22,45,1)'
        }
      };
	 

      var data = [trace[j]];
	  var temp, temp_range;
	  if(j==0){
		  temp = 'Temperatura';
		  temp_range = [10,35];
	  }else{
		  temp = 'Humidade';
		  temp_range = [40,100];
	  }
      var layout = {
        shapes: g_rects[j],
        title: changeTitle(),
        height: 367,
        width: 550,
        xaxis: {
          autotick: false,
          tick0: g_x[j][0],
          dtick: 25,
          range: [g_x[j][0], g_x[j][g_x[j].length - 1]],
          title: 'Hora',
          tickformat: "%H"
        },
        yaxis: {
          range: temp_range,
		  title: temp
        }
      }

      Plotly.newPlot('grafico' + (j+1), data, layout, {displaylogo: false, displayModeBar: false});

    }*/
  });
});

function change_data_chart(graph){
	if(graph=="temp"){
		//TEMPERATURA
	/* Example points for debuging: */
      trace[0] = {
        x: g_x[0],
        y: g_y[0],
        mode: 'lines',
		name: 'interior',
        line: {
          dash: 'lines',
          width: 4,
          color: 'rgba(238,22,45,1)'
        }
      };
	  var data;
	  if(both){
		trace[2] = {
			x: g_x[0],
			y: g_y[2],
			mode: 'lines',
			name: 'exterior',
			line: {
			  dash: 'lines',
			  width: 4,
			  color: 'rgba(0,0,255,1)'
			}
		};
		data = [trace[0],trace[2]];
	  }else{
		data = [trace[0]];
	  }
      
      var layout = {
        shapes: g_rects[0],
        title: changeTitle(),
        height: 367,
        width: 550,
        xaxis: {
          autotick: false,
          tick0: g_x[0][0],
          dtick: 25,
          range: [g_x[0][0], g_x[0][g_x[0].length - 1]],
          title: 'Hora',
          tickformat: "%H"
        },
        yaxis: {
          range: [10, 35],
          title: 'Temperatura'
        }
	  };
		Plotly.newPlot('grafico1' , data, layout, {displaylogo: false, displayModeBar: false});
	}else{
		//HUMIDADE
		/* Example points for debuging: */
      trace[1] = {
        x: g_x[1],
        y: g_y[1],
        mode: 'lines',
		name: 'interior',
        line: {
          dash: 'lines',
          width: 4,
          color: 'rgba(238,22,45,1)'
        }
      };
	  var data;
	  if(both){
		trace[3] = {
			x: g_x[0],
			y: g_y[3],
			mode: 'lines',
			name: 'exterior',
			line: {
			  dash: 'lines',
			  width: 4,
			  color: 'rgba(0,0,255,1)'
			}
		};
		data = [trace[1],trace[3]];
	  }else{
		data = [trace[1]];
	  }
      var layout = {
        shapes: g_rects[1],
        title: changeTitle(),
        height: 367,
        width: 550,
        xaxis: {
          autotick: false,
          tick0: g_x[1][0],
          dtick: 25,
          range: [g_x[1][0], g_x[1][g_x[1].length - 1]],
          title: 'Hora',
          tickformat: "%H"
        },
        yaxis: {
          range: [40,100],
          title: 'Humidade'
        }
	  };
		Plotly.newPlot('grafico2' , data, layout, {displaylogo: false, displayModeBar: false});
	}

}

function change_chart(option){
	var type_radio, type_data;
	if(option=="temp"){
		type_radio = 'input[name=optradio_temp]:checked';
		type_data = "temp";
	}else{
		type_radio = 'input[name=optradio_hum]:checked';
		type_data = "hum";
	}
	var status = $(type_radio).val();
	if(status=="int"){
	/*	if(both == false)
			current_file = current_file.replace('Ext','Int');
		else
			current_file = current_file.replace('Both','Int');
		*/
		both = true;
		aux = current_file.split('_');
		current_file = aux[0] + '_Both.csv';
		readBothCVSFile(aux[0]+'_Int.csv',aux[0]+'_Ext.csv');
		change_data_chart(type_data);
	}else if(status=="ext"){
		/*if(both == false)
			current_file = current_file.replace('Int','Ext');
		else
			current_file = current_file.replace('Both','Ext');*/
		both = true;
		aux = current_file.split('_');
		current_file = aux[0] + '_Both.csv';
		readBothCVSFile(aux[0]+'_Int.csv',aux[0]+'_Pre.csv');
		change_data_chart(type_data);
		
	}else{
		both = true;
		aux = current_file.split('_');
		current_file = aux[0] + '_Both.csv';
		readBothCVSFile(aux[0]+'_Int.csv',aux[0]+'_Ext.csv');
		change_data_chart(type_data);
		console.log("both");
	}
}


function available(date) {
  dmy = date.getDate() + "-" + (date.getMonth()+1) + "-" + date.getFullYear();
  if ($.inArray(dmy, availableDates) != -1) {
    return [true, "","Available"];
  } else {
    return [false,"","unAvailable"];
  }
}