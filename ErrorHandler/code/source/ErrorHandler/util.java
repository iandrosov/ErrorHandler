package ErrorHandler;

// -----( B2B Java Code Template v1.2
// -----( CREATED: Fri Nov 01 10:26:47 PST 2002
// -----( ON-HOST: USBSVLSVC1W3456.philips.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import java.util.*;
import java.io.*;
import com.wm.app.b2b.server.Session;
import com.wm.app.b2b.server.ServerAPI;
import java.net.InetAddress;
// --- <<B2B-END-IMPORTS>> ---

public final class util
{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void getSmtpServer (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(getSmtpServer)>> ---
		// @sigtype java 3.5
		// [o] field:0:required smtpServer
		// [o] field:0:required e_mail
			Properties config = System.getProperties();
			String str = config.getProperty("watt.server.smtpServer");
			String email = config.getProperty("watt.server.errorMail");
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		IDataUtil.put( pipelineCursor, "smtpServer", str );
		IDataUtil.put( pipelineCursor, "e_mail", email );
		pipelineCursor.destroy();
		// --- <<B2B-END>> ---

                
	}


    public static final Values get_object_from_session (Values in)
    {
        Values out = in;
		// --- <<B2B-START(get_object_from_session)>> ---
		// @sigtype java 3.0
		// [i] field:0:required objectName
		// [o] object:0:required object
	
	String strObjectName = in.getString("objectName");
	Session session = Service.getSession();
	out.put("object", session.get(strObjectName));
		// --- <<B2B-END>> ---
        return out;
                
	}



	public static final void get_service_name (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(get_service_name)>> ---
		// @sigtype java 3.5
		// [o] field:0:required serviceName
		IDataHashCursor curs = pipeline.getHashCursor();
		String caller = "SVC - ";
		
		try 
		{
			Thread t = Thread.currentThread();
			com.wm.app.b2b.server.ServerThread st = (com.wm.app.b2b.server.ServerThread)t;
			java.util.Stack stack = st.getState().getCallStack();
		
			for (int i = 0; i < stack.size(); i++)
			{
				if (i < stack.size() - 1)
				{
					caller += stack.elementAt(i).toString();
					caller += " ";
				}
			}
		
		} 
		catch (Exception e) 
		{ 
			caller = new String("non-determinable"); 
		}
		curs.last();
		curs.insertAfter("serviceName", caller);
		// --- <<B2B-END>> ---

                
	}


    public static final Values put_object_in_session (Values in)
    {
        Values out = in;
		// --- <<B2B-START(put_object_in_session)>> ---
		// @sigtype java 3.0
		// [i] object:0:required object
		// [i] field:0:required objectName

	Object obj = in.get("object");
	String strObjectName = in.getString("objectName");
	Session session = Service.getSession();

	// Data type
	Object tobj = session.get(strObjectName);
	if (tobj != null)
		session.remove(strObjectName);

	session.put(strObjectName, obj);
		// --- <<B2B-END>> ---
        return out;
                
	}
}

