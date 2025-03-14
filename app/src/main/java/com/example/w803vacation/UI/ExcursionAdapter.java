package com.example.w803vacation.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.w803vacation.R;
import com.example.w803vacation.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursion> xExcursions;

    private final Context context;

    private final LayoutInflater xInflator;
    private String vacationStartDate;
    private String vacationEndDate;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {

        private final TextView excursionItemView;

        private final TextView excursionItemView2;


        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView6);
            excursionItemView2 = itemView.findViewById(R.id.textView7);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Excursion current = xExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("name", current.getExcursionName());
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("excursionDate", current.getExcursionDate());
                    intent.putExtra("vacationStartDate", vacationStartDate);
                    intent.putExtra("vacationEndDate", vacationEndDate);
                    context.startActivity(intent);
                }
            });
        }
    }

    public ExcursionAdapter (Context context) {
        xInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = xInflator.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if(xExcursions!=null){
            Excursion current=xExcursions.get(position);
            String name=current.getExcursionName();
            int vacaID = current.getVacationID();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(Integer.toString(vacaID));
        }
        else{
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemView.setText("No excursion id");
        }
    }

    public void setExcursions(List<Excursion> excursions) {
        xExcursions = excursions;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (xExcursions!=null) return xExcursions.size();
        else return 0;
    }

}
