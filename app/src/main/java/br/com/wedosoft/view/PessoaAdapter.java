package br.com.wedosoft.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.wedosoft.consomews.R;
import br.com.wedosoft.model.PessoaModel;

public class PessoaAdapter extends BaseAdapter{
	private Context context;
	private List<?> listaO;

	// RECEBO O CONTEXTO DA TELA E UMA LISTA DE OBJETO DOS ITENS FILTRADOS
	public PessoaAdapter(Context context, List<?> listaO){
		this.context = context;
		this.listaO = listaO;
	}

	@Override
	public int getCount() {
		return listaO.size();
	}

	@Override
	public Object getItem(int position) {
		return listaO.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;

			PessoaModel pessoa = (PessoaModel) listaO.get(position);

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.content_adapter, null);
			view.setTag(pessoa.getId());

			ImageView im_pessoa = view.findViewById(R.id.im_pessoa);
			TextView te_nome = view.findViewById(R.id.te_nome);
			TextView te_email = view.findViewById(R.id.te_email);

			if (pessoa.getImagem() != null) {
				//decode base64 string to image
				byte[] imageBytes = Base64.decode(pessoa.getImagem().getImagem(), Base64.DEFAULT);
				Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

				im_pessoa.setImageBitmap(decodedImage);
			}

			te_nome.setText(String.valueOf(pessoa.getNome()));
			te_email.setText(pessoa.getEmail());

		return view;
	}
}

/*

ImageView image =(ImageView)findViewById(R.id.image);

        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //decode base64 string to image
        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        image.setImageBitmap(decodedImage);
 */