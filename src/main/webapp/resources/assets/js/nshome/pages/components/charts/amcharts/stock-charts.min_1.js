"use strict"; var KTamChartsStockChartsDemo = {init:function(){var e, t, a, o, l; t = [], a = [], o = [], l = [], function(){var e = new Date; e.setDate(e.getDate() - 500), e.setHours(0, 0, 0, 0); for (var r = 0; r < 500; r++){var i = new Date(e); i.setDate(i.getDate() + r); var d = Math.round(Math.random() * (40 + r)) + 100 + r, n = Math.round(Math.random() * (1e3 + r)) + 500 + 2 * r, s = Math.round(Math.random() * (100 + r)) + 200 + r, u = Math.round(Math.random() * (1e3 + r)) + 600 + 2 * r, p = Math.round(Math.random() * (100 + r)) + 200, h = Math.round(Math.random() * (1e3 + r)) + 600 + 2 * r, c = Math.round(Math.random() * (100 + r)) + 200 + r, g = Math.round(Math.random() * (100 + r)) + 600 + r; t.push({date:i, value:d, volume:n}), a.push({date:i, value:s, volume:u}), o.push({date:i, value:p, volume:h}), l.push({date:i, value:c, volume:g})}}(), AmCharts.makeChart("kt_amcharts_1", {rtl:KTUtil.isRTL(), type:"stock", theme:"light", dataSets:[{title:"first data set", fieldMappings:[{fromField:"value", toField:"value"}, {fromField:"volume", toField:"volume"}], dataProvider:t, categoryField:"date"}, {title:"second data set", fieldMappings:[{fromField:"value", toField:"value"}, {fromField:"volume", toField:"volume"}], dataProvider:a, categoryField:"date"}, {title:"third data set", fieldMappings:[{fromField:"value", toField:"value"}, {fromField:"volume", toField:"volume"}], dataProvider:o, categoryField:"date"}, {title:"fourth data set", fieldMappings:[{fromField:"value", toField:"value"}, {fromField:"volume", toField:"volume"}], dataProvider:l, categoryField:"date"}], panels:[{showCategoryAxis:!1, title:"Value", percentHeight:70, stockGraphs:[{id:"g1", valueField:"value", comparable:!0, compareField:"value", balloonText:"[[title]]:<b>[[value]]</b>", compareGraphBalloonText:"[[title]]:<b>[[value]]</b>"}], stockLegend:{periodValueTextComparing:"[[percents.value.close]]%", periodValueTextRegular:"[[value.close]]"}}, {title:"Volume", percentHeight:30, stockGraphs:[{valueField:"volume", type:"column", showBalloon:!1, fillAlphas:1}], stockLegend:{periodValueTextRegular:"[[value.close]]"}}], chartScrollbarSettings:{graph:"g1"}, chartCursorSettings:{valueBalloonsEnabled:!0, fullWidth:!0, cursorAlpha:.1, valueLineBalloonEnabled:!0, valueLineEnabled:!0, valueLineAlpha:.5}, periodSelector:{position:"left", periods:[{period:"MM", selected:!0, count:1, label:"1 month"}, {period:"YYYY", count:1, label:"1 year"}, {period:"YTD", label:"YTD"}, {period:"MAX", label:"MAX"}]}, dataSetSelector:{position:"left"}, export:{enabled:!0}}), e = [], function(){var t = new Date(2012, 0, 1); t.setDate(t.getDate() - 500), t.setHours(0, 0, 0, 0); for (var a = 0; a < 500; a++){var o = new Date(t); o.setDate(o.getDate() + a); var l = Math.round(Math.random() * (40 + a)) + 100 + a, r = Math.round(1e8 * Math.random()); e.push({date:o, value:l, volume:r})}}(), AmCharts.makeChart("kt_amcharts_2", {type:"stock", theme:"light", dataSets:[{color:"#b0de09", fieldMappings:[{fromField:"value", toField:"value"}, {fromField:"volume", toField:"volume"}], dataProvider:e, categoryField:"date", stockEvents:[{date:new Date(2010, 8, 19), type:"sign", backgroundColor:"#85CDE6", graph:"g1", text:"S", description:"This is description of an event"}, {date:new Date(2010, 10, 19), type:"flag", backgroundColor:"#FFFFFF", backgroundAlpha:.5, graph:"g1", text:"F", description:"Some longer\ntext can also\n be added"}, {date:new Date(2010, 11, 10), showOnAxis:!0, backgroundColor:"#85CDE6", type:"pin", text:"X", graph:"g1", description:"This is description of an event"}, {date:new Date(2010, 11, 26), showOnAxis:!0, backgroundColor:"#85CDE6", type:"pin", text:"Z", graph:"g1", description:"This is description of an event"}, {date:new Date(2011, 0, 3), type:"sign", backgroundColor:"#85CDE6", graph:"g1", text:"U", description:"This is description of an event"}, {date:new Date(2011, 1, 6), type:"sign", graph:"g1", text:"D", description:"This is description of an event"}, {date:new Date(2011, 3, 5), type:"sign", graph:"g1", text:"L", description:"This is description of an event"}, {date:new Date(2011, 3, 5), type:"sign", graph:"g1", text:"R", description:"This is description of an event"}, {date:new Date(2011, 5, 15), type:"arrowUp", backgroundColor:"#00CC00", graph:"g1", description:"This is description of an event"}, {date:new Date(2011, 6, 25), type:"arrowDown", backgroundColor:"#CC0000", graph:"g1", description:"This is description of an event"}, {date:new Date(2011, 8, 1), type:"text", graph:"g1", text:"Longer text can\nalso be displayed", description:"This is description of an event"}]}], panels:[{title:"Value", stockGraphs:[{id:"g1", valueField:"value"}], stockLegend:{valueTextRegular:" ", markerType:"none"}}], chartScrollbarSettings:{graph:"g1"}, chartCursorSettings:{valueBalloonsEnabled:!0, graphBulletSize:1, valueLineBalloonEnabled:!0, valueLineEnabled:!0, valueLineAlpha:.5}, periodSelector:{periods:[{period:"DD", count:10, label:"10 days"}, {period:"MM", count:1, label:"1 month"}, {period:"YYYY", count:1, label:"1 year"}, {period:"YTD", label:"YTD"}, {period:"MAX", label:"MAX"}]}, panelsSettings:{usePrefixes:!0}, export:{enabled:!0}}), function(){var e = function(){var e = [], t = new Date(2012, 0, 1); t.setDate(t.getDate() - 500), t.setHours(0, 0, 0, 0); for (var a = 0; a < 500; a++){var o = new Date(t); o.setDate(o.getDate() + a); var l = Math.round(Math.random() * (40 + a)) + 100 + a; e.push({date:o, value:l})}return e}(); AmCharts.makeChart("kt_amcharts_3", {type:"stock", theme:"light", dataSets:[{color:"#b0de09", fieldMappings:[{fromField:"value", toField:"value"}], dataProvider:e, categoryField:"date"}], panels:[{showCategoryAxis:!0, title:"Value", eraseAll:!1, allLabels:[{x:0, y:115, text:"Click on the pencil icon on top-right to start drawing", align:"center", size:16}], stockGraphs:[{id:"g1", valueField:"value", useDataSetColors:!1}], stockLegend:{valueTextRegular:" ", markerType:"none"}, drawingIconsEnabled:!0}], chartScrollbarSettings:{graph:"g1"}, chartCursorSettings:{valueBalloonsEnabled:!0}, periodSelector:{position:"bottom", periods:[{period:"DD", count:10, label:"10 days"}, {period:"MM", count:1, label:"1 month"}, {period:"YYYY", count:1, label:"1 year"}, {period:"YTD", label:"YTD"}, {period:"MAX", label:"MAX"}]}})}(), function(){var e = function(){var e = [], t = new Date(2012, 0, 1); t.setDate(t.getDate() - 1e3), t.setHours(0, 0, 0, 0); for (var a = 0; a < 1e3; a++){var o = new Date(t); o.setHours(0, a, 0, 0); var l = Math.round(Math.random() * (40 + a)) + 100 + a, r = Math.round(1e8 * Math.random()); e.push({date:o, value:l, volume:r})}return e}(); AmCharts.makeChart("kt_amcharts_4", {type:"stock", theme:"light", categoryAxesSettings:{minPeriod:"mm"}, dataSets:[{color:"#b0de09", fieldMappings:[{fromField:"value", toField:"value"}, {fromField:"volume", toField:"volume"}], dataProvider:e, categoryField:"date"}], panels:[{showCategoryAxis:!1, title:"Value", percentHeight:70, stockGraphs:[{id:"g1", valueField:"value", type:"smoothedLine", lineThickness:2, bullet:"round"}], stockLegend:{valueTextRegular:" ", markerType:"none"}}, {title:"Volume", percentHeight:30, stockGraphs:[{valueField:"volume", type:"column", cornerRadiusTop:2, fillAlphas:1}], stockLegend:{valueTextRegular:" ", markerType:"none"}}], chartScrollbarSettings:{graph:"g1", usePeriod:"10mm", position:"top"}, chartCursorSettings:{valueBalloonsEnabled:!0}, periodSelector:{position:"top", dateFormat:"YYYY-MM-DD JJ:NN", inputFieldWidth:150, periods:[{period:"hh", count:1, label:"1 hour", selected:!0}, {period:"hh", count:2, label:"2 hours"}, {period:"hh", count:5, label:"5 hour"}, {period:"hh", count:12, label:"12 hours"}, {period:"MAX", label:"MAX"}]}, panelsSettings:{usePrefixes:!0}, export:{enabled:!0, position:"bottom-right"}})}(), function(){var e = []; !function(){var t = new Date; t.setHours(0, 0, 0, 0), t.setDate(t.getDate() - 2e3); for (var a = 0; a < 2e3; a++){var o = new Date(t); o.setDate(o.getDate() + a); var l, r, i = Math.round(30 * Math.random() + 100), d = i + Math.round(15 * Math.random() - 10 * Math.random()); l = i < d?i - Math.round(5 * Math.random()):d - Math.round(5 * Math.random()), r = i < d?d + Math.round(5 * Math.random()):i + Math.round(5 * Math.random()); var n = Math.round(Math.random() * (1e3 + a)) + 100 + a, s = Math.round(30 * Math.random() + 100); e[a] = {date:o, open:i, close:d, high:r, low:l, volume:n, value:s}}}(), AmCharts.makeChart("kt_amcharts_5", {type:"stock", theme:"light", dataSets:[{fieldMappings:[{fromField:"open", toField:"open"}, {fromField:"close", toField:"close"}, {fromField:"high", toField:"high"}, {fromField:"low", toField:"low"}, {fromField:"volume", toField:"volume"}, {fromField:"value", toField:"value"}], color:"#7f8da9", dataProvider:e, title:"West Stock", categoryField:"date"}, {fieldMappings:[{fromField:"value", toField:"value"}], color:"#fac314", dataProvider:e, compared:!0, title:"East Stock", categoryField:"date"}], panels:[{title:"Value", showCategoryAxis:!1, percentHeight:70, valueAxes:[{id:"v1", dashLength:5}], categoryAxis:{dashLength:5}, stockGraphs:[{type:"candlestick", id:"g1", openField:"open", closeField:"close", highField:"high", lowField:"low", valueField:"close", lineColor:"#7f8da9", fillColors:"#7f8da9", negativeLineColor:"#db4c3c", negativeFillColors:"#db4c3c", fillAlphas:1, useDataSetColors:!1, comparable:!0, compareField:"value", showBalloon:!1, proCandlesticks:!0}], stockLegend:{valueTextRegular:void 0, periodValueTextComparing:"[[percents.value.close]]%"}}, {title:"Volume", percentHeight:30, marginTop:1, showCategoryAxis:!0, valueAxes:[{dashLength:5}], categoryAxis:{dashLength:5}, stockGraphs:[{valueField:"volume", type:"column", showBalloon:!1, fillAlphas:1}], stockLegend:{markerType:"none", markerSize:0, labelText:"", periodValueTextRegular:"[[value.close]]"}}], chartScrollbarSettings:{graph:"g1", graphType:"line", usePeriod:"WW"}, chartCursorSettings:{valueLineBalloonEnabled:!0, valueLineEnabled:!0}, periodSelector:{position:"bottom", periods:[{period:"DD", count:10, label:"10 days"}, {period:"MM", selected:!0, count:1, label:"1 month"}, {period:"YYYY", count:1, label:"1 year"}, {period:"YTD", label:"YTD"}, {period:"MAX", label:"MAX"}]}, export:{enabled:!0}})}()}}; jQuery(document).ready(function(){KTamChartsStockChartsDemo.init()});