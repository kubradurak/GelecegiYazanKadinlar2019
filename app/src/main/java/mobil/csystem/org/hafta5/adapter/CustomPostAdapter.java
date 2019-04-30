package mobil.csystem.org.hafta5.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobil.csystem.org.hafta5.R;
import mobil.csystem.org.hafta5.model.ListViewModelClass;

public class CustomPostAdapter extends BaseAdapter {

    LayoutInflater Layout_inflater;
    List<ListViewModelClass> ListArray;

    public CustomPostAdapter(LayoutInflater layout_inflater, List<ListViewModelClass> listArray) {
        this.Layout_inflater = layout_inflater;
        this.ListArray = listArray;
    }

    @Override
    public int getCount() {

        return ListArray.size()
                ;
    }


    @Override
    public Object getItem(int position) {

        return ListArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = Layout_inflater.inflate(R.layout.listview_satir_tasarim, null);


        ImageView img_view = v.findViewById(R.id.post_picture);
        TextView city_name = v.findViewById(R.id.post_title);
        TextView city_description = v.findViewById(R.id.post_description);

        ListViewModelClass modelClass = ListArray.get(position);

        img_view.setImageResource(modelClass.getCity_picture());
        city_name.setText(modelClass.getCity_name());
        city_description.setText(modelClass.getCity_about());


        return v;
    }
}
