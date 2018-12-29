package saurabh.com.speechtotext.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import saurabh.com.speechtotext.Data.DictionaryItem;
import saurabh.com.speechtotext.R;

public class AdapterRvCell extends RecyclerView.Adapter<AdapterRvCell.MyViewHolder> {

    private AppCompatActivity context;
    private List<DictionaryItem> dictionaryItems;

    public AdapterRvCell(AppCompatActivity context, List<DictionaryItem> dictionaryItems) {
        this.context=context;
        this.dictionaryItems=dictionaryItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_word.setText(dictionaryItems.get(position).getWord());
        holder.tv_frequency.setText(Integer.toString(dictionaryItems.get(position).getFrequency()));
    }

    @Override
    public int getItemCount() {
        return dictionaryItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_word,tv_frequency;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_word= itemView.findViewById(R.id.tv_word);
            tv_frequency=itemView.findViewById(R.id.tv_fraquency);
        }
    }
}
