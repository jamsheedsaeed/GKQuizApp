package jk.techplus.quiz.gkQuiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class SolvedQuestionAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<MultipleChoice> items; //data source of the list adapter

    //public constructor
    public SolvedQuestionAdapter(Context context, ArrayList<MultipleChoice> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layoutsolveditems, parent, false);
        }

        // get current item to be displayed
        MultipleChoice currentItem = (MultipleChoice) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.text_view_item_name);

        TextView textViewItemNamenew = (TextView)
                convertView.findViewById(R.id.answer);


        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getQuestion());
        textViewItemNamenew.setText("Ans: "+currentItem.getAnswer());

        // returns the view for the current row
        return convertView;
    }
}