package pt.fantabulastic.androidproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class BroadCast extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		abortBroadcast();
		
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try
		{
			if (bundle != null)
			{
				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++)
				{
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					byte[] data = currentMessage.getUserData();
					String message = "";
					if (data != null)
			        {
			          for (int index = 0; index < data.length; ++index)
			          {
			        	  message += Character.toString((char) data[index]);
			          }
			        }

					Log.i("SmsReceiver", "senderNum: " + senderNum + "; msg: " + message);

					// Show Alert
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, "senderNum: " + senderNum + ", msg: " + message, duration);
					toast.show();
				}
			}
		}
		catch (Exception e)
		{
			Log.e("SmsReceiver", "Exception smsReceiver" + e);
		}
	}
}