package com.example.inseo.js_go;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by INSEO on 16. 5. 9..
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static public final int TypeBookCard = 0;
    static public final int TypeDefault = 1;
    private int size;
    private ArrayList<DataSet> dataSet;

        public static class DataSet {
        private int type;
        ArrayList<String> dataList;
        public DataSet(int t,ArrayList<String>d) {
            type = t;
            dataList = d;
        }

        public int type() {
            return type;
        }

        public ArrayList<String> dataList() {
            return dataList;
        }
    }

    public static class BookCard extends RecyclerView.ViewHolder {

        TextView bookName;
        TextView bookDue;
        TextView bookLent;

        public BookCard(View itemView) {
            super(itemView);

            bookName = (TextView)itemView.findViewById(R.id.bookName);
            bookDue = (TextView) itemView.findViewById(R.id.bookDue);
            bookLent = (TextView) itemView.findViewById(R.id.bookLent);
        }
        public int getType() {
            return RecyclerAdapter.TypeBookCard;
        }
    } // ViewHolder

    public static class DefaultCard extends RecyclerView.ViewHolder {
        TextView textView;

        public DefaultCard(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.defaultText);

        }
        public int getType() {
            return RecyclerAdapter.TypeDefault;
        }
    }// ViewHolder

    public RecyclerAdapter() {
        super();
        dataSet = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position).type();
//        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder vh;
        switch (viewType) {
            case RecyclerAdapter.TypeBookCard:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_book, parent, false);
                vh = new RecyclerAdapter.BookCard(v); //new RecyclerView.ViewHolder(v);
                return vh;
            case RecyclerAdapter.TypeDefault:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_default, parent, false);
                vh = new RecyclerAdapter.DefaultCard(v);
                return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArrayList<String> data;
        switch (dataSet.get(position).type()) {
            case RecyclerAdapter.TypeBookCard:
                BookCard bookCard = (BookCard) holder;
                data = dataSet.get(position).dataList();

                bookCard.bookName.setText(data.get(0));
                if(data.get(0).length()>=25) {
                    bookCard.bookName.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                }
                bookCard.bookDue.setText(data.get(1));
                bookCard.bookLent.setText(data.get(2));
                break;

            case RecyclerAdapter.TypeDefault:
                DefaultCard defaultCard = (DefaultCard) holder;
                data = dataSet.get(position).dataList();
                for(String str : data) {
                    defaultCard.textView.append(str+"\n");
                }
        }
    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.textView.append("(position:"+position+")");
//    }

    @Override
    public int getItemCount() {
        if(dataSet != null) {
            return dataSet.size();
        }
        return 0;
    }

    public void deleteItem(int idx) {
        dataSet.remove(idx);
        notifyDataSetChanged();
    }

    public void addItem(RecyclerAdapter.DataSet ds) {
        dataSet.add(ds);
        notifyDataSetChanged();
    }

    public void reLoad() {
        notifyDataSetChanged();
    }

    public void reLoad(ArrayList<RecyclerAdapter.DataSet> dsList) {
        this.dataSet = dsList;
        notifyDataSetChanged();
    }



//    public void
}
