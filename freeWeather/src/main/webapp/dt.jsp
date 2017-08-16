<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
  <style type="text/css">
    body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
    #allmap{height:100%;width:100%;}
    #r-result{width:100%;}
  </style>
 <script src="${pageContext.request.contextPath}/js/jquery/jquery-3.1.1.min.js"></script>
  <title>添加/删除地面叠加层</title>
</head>
<body>
  <div id="allmap">
  </div>
</body>

<script>
var text="Polygon"
$.getJSON("json/china_all.json",function(data){
	debugger;
	// 轮廓
	//decode(data)
	// 所有
	decode2(data)
});

function decode2(json) {
    if (!json.UTF8Encoding) {
        return json;
    }
    var features = json.features;

    var num=0;
    for (var f = 0; f < features.length; f++)
    {
    	var feature = features[f];
        var geometry = feature.geometry;
        var coordinates = geometry.coordinates;
    	num+=coordinates.length;
    }
    text+=(";"+num);
    
    for (var f = 0; f < features.length; f++) {
        var feature = features[f];
        var geometry = feature.geometry;
        var coordinates = geometry.coordinates;
        var encodeOffsets = geometry.encodeOffsets;
        for (var c = 0; c < coordinates.length; c++) {
            var coordinate = coordinates[c];

            if (geometry.type === 'Polygon') {
                coordinates[c] = decodePolygon(
                    coordinate,
                    encodeOffsets[c]
                );
            }
            else if (geometry.type === 'MultiPolygon') {
                for (var c2 = 0; c2 < coordinate.length; c2++) {
                    var polygon = coordinate[c2];
                    coordinate[c2] = decodePolygon(
                        polygon,
                        encodeOffsets[c][c2]
                    );
                }
            }
        }
    }
    // Has been decoded
    //json.UTF8Encoding = false;
    //return json;
    $("#allmap").html(text);
}

function decode(json) {
    if (!json.UTF8Encoding) {
        return json;
    }
    var features = json.features;

    for (var f = 0; f < features.length; f++) {
        var feature = features[f];
        var geometry = feature.geometry;
        var coordinates = geometry.coordinates;
        var encodeOffsets = geometry.encodeOffsets;
        text+=(";"+coordinates.length);
        for (var c = 0; c < coordinates.length; c++) {
            var coordinate = coordinates[c];

            if (geometry.type === 'Polygon') {
                coordinates[c] = decodePolygon(
                    coordinate,
                    encodeOffsets[c]
                );
            }
            else if (geometry.type === 'MultiPolygon') {
                for (var c2 = 0; c2 < coordinate.length; c2++) {
                    var polygon = coordinate[c2];
                    coordinate[c2] = decodePolygon(
                        polygon,
                        encodeOffsets[c][c2]
                    );
                }
            }
        }
    }
    // Has been decoded
    //json.UTF8Encoding = false;
    //return json;
    $("#allmap").html(text);
}
function decodePolygon(coordinate, encodeOffsets) {
    var result = [];
    var prevX = encodeOffsets[0];
    var prevY = encodeOffsets[1];
    text+=(";"+coordinate.length/2);
    for (var i = 0; i < coordinate.length; i += 2) {
        var x = coordinate.charCodeAt(i) - 64;
        var y = coordinate.charCodeAt(i + 1) - 64;
        // ZigZag decoding
        x = (x >> 1) ^ (-(x & 1));
        y = (y >> 1) ^ (-(y & 1));
        // Delta deocding
        x += prevX;
        y += prevY;

        prevX = x;
        prevY = y;
        // Dequantize
        //result.push([x / 1024, y / 1024]);
        text+=(";"+(x / 1024)+","+(y / 1024));
    }

    //return result;
}
</script>
</html>