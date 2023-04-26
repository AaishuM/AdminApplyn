package com.uniguide.adminapply.Admin;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.uniguide.adminapply.R;

import java.util.ArrayList;

public class StatisticsGraph extends AppCompatActivity {

    ArrayList<BarEntry> barArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_graph);
        BarChart barChart = findViewById(R.id.barcharts);
        getDate();
       /* barArrayList = new ArrayList<>();
        // Add data to the bar chart
        barArrayList.add(new BarEntry(1, 20));
        barArrayList.add(new BarEntry(2, 35));
        barArrayList.add(new BarEntry(3, 45));
        barArrayList.add(new BarEntry(4, 60));
        barArrayList.add(new BarEntry(5, 80));*/
        BarDataSet barDataSet = new BarDataSet(barArrayList, "\n" +
                "#average student getting admission to different department");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors (ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
        //barChart.animateY(2000);
    }

    private void getDate(){
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry( 2f, 10, "Computer Science" ));
        barArrayList.add(new BarEntry( 4f, 18, "Civil Engineering" ));
        barArrayList.add(new BarEntry( 3f, 12, "Music And Arts " ));
        barArrayList.add(new BarEntry( 1f, 24, "Business School" ));
        barArrayList.add(new BarEntry( 4f, 7 , "Law School"));
    }
}
