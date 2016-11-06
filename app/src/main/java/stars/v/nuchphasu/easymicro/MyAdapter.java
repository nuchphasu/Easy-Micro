package stars.v.nuchphasu.easymicro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by nuchphasu-v on 06/11/2016.
 */

public class MyAdapter extends BaseAdapter {

    //Explicit
    private Context context;
    private String[] nameStrings, imageStrings;

    public MyAdapter(Context context, String[] nameStrings, String[] imageStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.imageStrings = imageStrings;
    }//MyAdapter

    @Override
    public int getCount() {
        return nameStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.my_listview, viewGroup, false);
        TextView textView = (TextView) view1.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) view1.findViewById(R.id.imageView3);
        textView.setText(nameStrings[i]);
        Picasso.with(context).load(imageStrings[i]).into(imageView);

        return view1;
    }
}//Main Class
