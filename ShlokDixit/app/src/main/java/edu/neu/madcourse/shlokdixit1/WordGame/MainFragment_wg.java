/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
***/
package edu.neu.madcourse.shlokdixit1.WordGame;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.madcourse.shlokdixit1.R;

public class MainFragment_wg extends Fragment {

   private AlertDialog mDialog;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View rootView =
            inflater.inflate(R.layout.fragment_main_wg, container, false);
      // Handle buttons here...
      View newButton = rootView.findViewById(R.id.new_button_wg);
      View continueButton = rootView.findViewById(R.id.continue_button_wg);
      View aboutButton = rootView.findViewById(R.id.about_button_wg);
      View quitButton = rootView.findViewById(R.id.quit_button_wg);
      View ack_Button = rootView.findViewById(R.id.ack_button_wg);
      newButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(getActivity(), GameActivity_wg.class);
            getActivity().startActivity(intent);
         }
      });
      continueButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
           /* Intent intent = new Intent(getActivity(), GameActivity_wg.class);
            intent.putExtra(GameActivity_wg.KEY_RESTORE, true);
            getActivity().startActivity(intent);*/
         }
      });
      quitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            getActivity().finish();
         }
      });

      aboutButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.about_text_wg);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.ok_label,
                  new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                        // nothing
                     }
                  });
            mDialog = builder.show();
         }
      });
      ack_Button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(getActivity(), WordGameCredits.class);
            getActivity().startActivity(intent);
         }
      });

      return rootView;
   }
   @Override
   public void onPause() {
      super.onPause();

      // Get rid of the about dialog if it's still up
      if (mDialog != null)
         mDialog.dismiss();
   }
}
