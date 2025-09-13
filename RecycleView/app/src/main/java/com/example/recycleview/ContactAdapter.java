package com.example.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private List<Contact> contactList;
    private List<Contact> contactListFull;
    private OnContactClickListener onContactClickListener;

    public interface OnContactClickListener {
        void onContactClick(int position);
    }

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFull = new ArrayList<>(contactList);
    }

    public void setOnContactClickListener(OnContactClickListener listener) {
        this.onContactClickListener = listener;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());

        if (contact.getImageUrl() != null && !contact.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(contact.getImageUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imgProfile);
        } else {
            holder.imgProfile.setImageResource(R.drawable.profile);
        }

        holder.itemView.setOnClickListener(v -> {
            if (onContactClickListener != null) {
                onContactClickListener.onContactClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addContact(Contact contact) {
        contactList.add(contact);
        contactListFull.add(contact);
        notifyItemInserted(contactList.size() - 1);
    }

    public void removeContact(int position) {
        if (position != -1) {
            contactList.remove(position);
            contactListFull.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void filter(String query) {
        contactList.clear();
        if (query.isEmpty()) {
            contactList.addAll(contactListFull);
        } else {
            query = query.toLowerCase().trim();
            for (Contact contact : contactListFull) {
                if (contact.getName().toLowerCase().contains(query)) {
                    contactList.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile;
        TextView tvName, tvPhone;

        public ContactViewHolder(View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.img_profile);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}