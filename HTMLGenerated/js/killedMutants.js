/**
 * Created by cazala on 25/02/16.
 */

$(function () {

    $(document).ready(function () {

        // Build the chart
        $('#stats').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'Nombre de mutants tués'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: 'Légende',
                colorByPoint: true,
                data: [{
                    name: 'Morts',
                    y: 80.00
                }, {
                    name: 'Survivants',
                    y: 17.00,
                    sliced: true,
                    selected: true
                }, {
                    name: 'Morts nés',
                    y: 3.00
                }]
            }]
        });
    });
});