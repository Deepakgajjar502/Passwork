package com.b2b.passwork.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.b2b.passwork.Adaptor.CustomGrid;
import com.b2b.passwork.R;

public class WorkspaceLayout extends AppCompatActivity {

    GridView grid;
    int[] imageId = new int[]{R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working_select, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working};
    String[] web = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace_layout);



        CustomGrid adapter = new CustomGrid(WorkspaceLayout.this, web, imageId);

        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });


    }
}