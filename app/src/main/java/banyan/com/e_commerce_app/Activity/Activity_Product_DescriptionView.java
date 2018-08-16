package banyan.com.e_commerce_app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import banyan.com.e_commerce_app.R;

public class Activity_Product_DescriptionView extends Activity
{
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productview_fragment);
        toolbar = (Toolbar)findViewById(R.id.search_toolbar);
        toolbar.setTitle("PRODUCT PAGE");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
       /* head_title = (TextView)findViewById(R.id.head_title);
        head_title.setText("PRODUCT PAGE");*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Product_DescriptionView.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }
}
