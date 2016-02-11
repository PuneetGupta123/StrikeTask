package dell.striketask;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 2/9/2016.
 */
public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {

    ArrayList<Email> emailArrayList;
    Long currentTime;
    public EmailAdapter(ArrayList<Email> emailArrayList) {
        this.emailArrayList = emailArrayList;
        currentTime=System.currentTimeMillis()/1000;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.email_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Email item = emailArrayList.get(position);
        viewHolder.personName.setText(item.getName());
        viewHolder.description.setText(item.getDescription());
        Long emailTime = Long.valueOf(item.getTimeStamp());
        viewHolder.time.setText(getTime(currentTime,emailTime));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int finalPosition=position+1;
                Snackbar.make(view,"Clicked on position "+String.valueOf(finalPosition),Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return emailArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView profile_pic;
        TextView personName;
        TextView description;
        TextView time ;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            profile_pic = (CircleImageView) itemView.findViewById(R.id.person_photo);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            description = (TextView) itemView.findViewById(R.id.email_description);
            time = (TextView) itemView.findViewById(R.id.time);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }

    public String getTime(Long currentTime, Long emailTime)
    {
        Long hour = Long.valueOf(60*60);
        Long day = Long.valueOf(hour*24);
        Long twoDays = Long.valueOf(day*2);
        Long week = Long.valueOf(day*7);
        Long year = Long.valueOf(day*365);
        Long elapsedTime = currentTime - emailTime;
        String answer="";
        if(elapsedTime<hour)
        {
            answer=String.valueOf(elapsedTime/60)+" m ago";
        }
        else if(elapsedTime>=hour&&elapsedTime<day)
        {
            answer=String.valueOf(elapsedTime/hour)+" h ago";
        }
        else if(elapsedTime>=day&&elapsedTime<twoDays)
        {
            answer="yesterday";
        }
        else if(elapsedTime>=twoDays&&elapsedTime<week)
        {
            answer=String.valueOf(elapsedTime/day) + " days ago";
        }
        else if(elapsedTime>=week&&elapsedTime<year)
        {
            Date date = new Date(emailTime*1000);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // the format of your date
            String formattedDate = sdf.format(date);
            answer=formattedDate.substring(0,5);
        }
        else
        {
            Long years=elapsedTime/year;
            if(years>=1&&years<2)
            {
                answer="about 1 year ago";
            }
            else
            {
                answer="about"+String.valueOf(years)+" years ago";
            }
        }

        return answer;
    }

}
