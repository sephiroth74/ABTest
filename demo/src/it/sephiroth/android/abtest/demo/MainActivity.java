package it.sephiroth.android.abtest.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import it.sephiroth.android.library.ab.AB;
import it.sephiroth.android.library.ab.ABTest;

public class MainActivity extends Activity
{
	private AB abtest;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // initialize the ab instance
        abtest = AB.getInstance(this);

        setTitle("Group: " + abtest.getGroup());

        findViewById(R.id.button).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("ABTest demo")
                        .setPositiveButton("Close", null)
                        .create();

                abtest.doABTest("demo", new ABTest() {
                    @Override
                    public void A() {
                        dialog.setMessage("A() test called!");
                    }

                    @Override
                    public void B() {
                        dialog.setMessage("B() test called");
                    }
                });

                dialog.show();
            }
        });
    }
}
