package it.sephiroth.android.abtest.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import it.sephiroth.android.library.ab.AB;
import it.sephiroth.android.library.ab.ABTest;
import it.sephiroth.android.library.ab.NTest;

public class MainActivity extends Activity
{
	private AB abtest;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        abtest = AB.getInstance(this);

        setTitle("Group: " + abtest.getGroup(30, 30, 40));

        Toast.makeText( this, "AB.VERSION: " + AB.VERSION, Toast.LENGTH_SHORT).show();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("ABTest demo")
                        .setPositiveButton("Close", null)
                        .create();

                abtest.doNTest("demo", new NTest() {
                    @Override
                    protected void run(int group) {
                        switch (group) {
                            case 0:
                                dialog.setMessage("A() test called!");
                                break;
                            case 1:
                                dialog.setMessage("B() test called!");
                                break;
                            case 2:
                                dialog.setMessage("C() test called!");
                                break;
                        }
                    }
                }, 30, 30, 40);

                dialog.show();
            }
        });
    }
}
