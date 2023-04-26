// This is the Java code for an ItemAdapter, which extends the RecyclerView.Adapter class
// It is used to display a list of items in a RecyclerView, and is used in conjunction with the item_list.xml layout file
// This adapter takes a Context object and an ArrayList of items as its constructor parameters
package com.uniguide.adminapply.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniguide.adminapply.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    Context context;

    ArrayList<items> list;

    public ItemAdapter(Context context, ArrayList<items> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        items items = list.get(position);
        holder.fullname.setText(items.getFullName());
        holder.DateofBirth.setText(items.getDateOfBirth());
        holder.CountryofBirth.setText(items.getCountryOfBirth());
        holder.PlaceofBirth.setText(items.getPlaceOfBirth());
        holder.CountryofResidence.setText(items.getCountryOfResidence());
        holder.Province.setText(items.getProvince());
        holder.Email.setText(items.getEmail());
        holder.Country.setText(items.getCountry());
        holder.Contact.setText(items.getContact());
        holder.School.setText(items.getSchool());
        holder.SchoolLocation.setText(items.getSchoolLocation());
        holder.SchoolCourse.setText(items.getSchoolCourse());
        holder.SchoolGPA.setText(items.getSchoolGPA());
        holder.College.setText(items.getCollege());
        holder.CollegeLocation.setText(items.getCollegeLocation());
        holder.CollegeCourse.setText(items.getCollegeCourse());
        holder.CollegeGPA.setText(items.getCollegeGPA());
        holder.University.setText(items.getUni());
        holder.UniversityLocation.setText(items.getUniLocation());
        holder.UniversityCourse.setText(items.getUniCourse());
        holder.UniversityGPA.setText(items.getUniGPA());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fullname,DateofBirth,CountryofBirth,PlaceofBirth,CountryofResidence,Province,Email,Country,Contact,School,SchoolLocation,SchoolCourse,
                SchoolGPA,College,CollegeLocation,CollegeCourse,CollegeGPA,University,UniversityLocation,UniversityCourse,UniversityGPA;;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.fullname);
            DateofBirth = itemView.findViewById(R.id.DateBirth);
            CountryofBirth = itemView.findViewById(R.id.CountryofBirth);
                    PlaceofBirth = itemView.findViewById(R.id.PlaceofBirth);
                    CountryofResidence = itemView.findViewById(R.id.CountryofResidence);
                    Province = itemView.findViewById(R.id.Province);
                    Email  = itemView.findViewById(R.id.myEmail);
                    Country = itemView.findViewById(R.id.Country);
                    Contact = itemView.findViewById(R.id.Contact);
                    School = itemView.findViewById(R.id.School);
                    SchoolLocation = itemView.findViewById(R.id.SchoolLocation);
                    SchoolCourse = itemView.findViewById(R.id.SchoolCourse);
                    SchoolGPA = itemView.findViewById(R.id.SchoolGPA);
                    College = itemView.findViewById(R.id.College);
                    CollegeLocation = itemView.findViewById(R.id.CollegeLocation);
                    CollegeCourse = itemView.findViewById(R.id.CollegeCourse);
                    CollegeGPA = itemView.findViewById(R.id.CollegeGPA);
                    University = itemView.findViewById(R.id.University);
                    UniversityLocation = itemView.findViewById(R.id.UniversityLocation);
                    UniversityCourse = itemView.findViewById(R.id.UniversityCourse);
                    UniversityGPA = itemView.findViewById(R.id.UniversityGPA);


        }
    }

}
