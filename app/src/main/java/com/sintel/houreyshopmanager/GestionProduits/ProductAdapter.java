package com.sintel.houreyshopmanager.GestionProduits;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sintel.houreyshopmanager.MainActivity;
import com.sintel.houreyshopmanager.Models.MaBaseDeDonneesHelper;
import com.sintel.houreyshopmanager.Models.Product;
import com.sintel.houreyshopmanager.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    int pos;

    @SuppressLint("NotifyDataSetChanged")
    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productadapter_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
        pos=(position+1);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.removeItem(position);
            }
        });

        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.updateItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName,textViewProductDescription,textViewProductPrice,textViewProductCategory,textViewProductQuantity,numero;
        ImageView modify, delete;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.product_name);
            textViewProductDescription = itemView.findViewById(R.id.product_Description);
            textViewProductPrice = itemView.findViewById(R.id.product_price);
            textViewProductCategory = itemView.findViewById(R.id.product_category);
            textViewProductQuantity = itemView.findViewById(R.id.product_quantity);
            numero = itemView.findViewById(R.id.numero);
            modify=itemView.findViewById(R.id.id_modify);
            delete=itemView.findViewById(R.id.id_delete);


        }

        public void bind(Product product) {
            numero.setText(""+(getAdapterPosition()+1));
            textViewProductName.setText(product.getName());
            textViewProductDescription.setText(product.getDescription());
            textViewProductPrice.setText(String.valueOf(product.getPrice()));
            textViewProductCategory.setText(String.valueOf(product.getCategory()));
            textViewProductQuantity.setText(String.valueOf(product.getQuantity()));
        }

        public void removeItem(int position) {

            // Récupérer le produit à supprimer
            Product product = productList.get(position);
            // Supprimer le produit de la base de données en utilisant Room
            MaBaseDeDonneesHelper databaseHelper = new MaBaseDeDonneesHelper(itemView.getContext());
            databaseHelper.deleteProduct(product.getId());
            productList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList.size());
        }

        @SuppressLint("SetTextI18n")
        public void updateItem(int position){
            // Récupérer le produit à supprimer
            Product product = productList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
            View view = inflater.inflate(R.layout.modify_activity, null);
            builder.setView(view);

            final AlertDialog dialog = builder.create();

            EditText editTextProductName = view.findViewById(R.id.editTextProductName1);
            EditText editTextProductDescription = view.findViewById(R.id.editTextProductDescription1);
            EditText editTextProductPrice = view.findViewById(R.id.editTextProductPrice1);
            EditText editTextProductQuantity = view.findViewById(R.id.editTextProductQuantity1);
            Spinner spinnerProductCategory = view.findViewById(R.id.spinnerProductCategory1);
            Button buttonUpdateProduct = view.findViewById(R.id.buttonAddProduct1);

            editTextProductName.setText(""+product.getName().trim());
            editTextProductDescription.setText(""+product.getDescription().trim());
            editTextProductPrice.setText(""+product.getPrice().trim());
            editTextProductQuantity.setText(""+product.getQuantity().trim());

           //  Définir la position de l'élément sélectionné dans le Spinner
            ArrayAdapter<String> categoryAdapter = (ArrayAdapter<String>) spinnerProductCategory.getAdapter();
           int categoryPosition = categoryAdapter.getPosition(product.getCategory());
           spinnerProductCategory.setSelection(categoryPosition);


            buttonUpdateProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String productName = editTextProductName.getText().toString();
                    String productDescription = editTextProductDescription.getText().toString();
                    String productPrice = editTextProductPrice.getText().toString();
                    String productQuantity = editTextProductQuantity.getText().toString();
                    String productCategory = spinnerProductCategory.getSelectedItem().toString();

                    if (productName.isEmpty() || productDescription.isEmpty() || productCategory.isEmpty()||productPrice.isEmpty()||productQuantity.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Erreur");
                        builder.setIcon(R.drawable.ic_baseline_info_24);
                        builder.setMessage("Remplir tous les champs !");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else {
                        // Mettre à jour le produit dans la base de données
                        product.setName(productName);
                        product.setDescription(productDescription);
                        product.setPrice(productPrice);
                        product.setQuantity(productQuantity);
                        product.setCategory(productCategory);

                        MaBaseDeDonneesHelper databaseHelper = new MaBaseDeDonneesHelper(v.getContext());
                        databaseHelper.updateProduct(product);

                        // Actualiser la liste des produits
                        productList.set(position, product);
                        notifyItemChanged(position);

                        dialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Message");
                        builder.setIcon(R.drawable.ok);
                        builder.setMessage("Modification effectuée avec succès !");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
            });


            dialog.show();
        }
    }
}
