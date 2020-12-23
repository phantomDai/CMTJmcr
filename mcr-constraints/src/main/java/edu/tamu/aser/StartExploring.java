package edu.tamu.aser;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import edu.tamu.aser.config.Configuration;
import edu.tamu.aser.trace.Trace;

public class StartExploring implements Runnable {

	private Trace traceObj;                        //the current trace
	private Vector<String> schedule_prefix;        //the prefix that genrates the trace
	private Queue<List<String>> exploreQueue;      //the seed interleavings

	public static class BoxInt {

		volatile int  value;

		BoxInt(int initial) {
			this.value = initial;
		}

		public synchronized int getValue() {
			return this.value;
		}

		public synchronized void increase() {
			this.value++;
		}

		public synchronized void decrease() {
			this.value--;
		}
	}

	public final static BoxInt executorsCount = new BoxInt(0);

	public StartExploring(Trace trace, Vector<String> prefix, Queue<List<String>> queue) {
		this.traceObj = trace;
		this.schedule_prefix = prefix;
		this.exploreQueue = queue;
	}

	public Trace getTrace() {
		return this.traceObj;
	}

//	public Vector<String> getCurrentSchedulePrefix() {
//		return this.schedule_prefix;
//	}

//	public Queue<List<String>> exploreQueue() {
//		return this.exploreQueue;
//	}

	/**
	 * start exploring other interleavings
	 * 
	 */
	public void run() {
		try {
			ExploreSeedInterleavings explore = new ExploreSeedInterleavings(exploreQueue);

			//load the trace
			traceObj.finishedLoading(true);

//			System.out.println("------trace----");
//			for (int t=0;t<=traceObj.getFullTrace().size()-1;t++){
//				System.out.println(traceObj.getFullTrace().get(t).getGID()+" "+
//								traceObj.getThreadIdNameMap().get(traceObj.getFullTrace().get(t).getTid())+" "+
//								traceObj.getFullTrace().get(t).getLabel()+" "+
////								trace.getSharedVarIdMap().get(SID)+" "+
//								traceObj.getFullTrace().get(t).getType() +" "+
//								traceObj.getFullTrace().get(t).getValue());
//			}
//			System.out.println("--------------shared------------");
//			System.out.println(traceObj.getSharedVariables());
			for (String sv : traceObj.getSharedVarIdMap().values()){
				PrintStream oldPrintStream = System.out;
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				System.setOut(new PrintStream(bos));
				System.out.println(sv);
				System.setOut(oldPrintStream);
//						traceBuffer = traceBuffer.append(bos.toString());
				FileWriter fileWriter = null;
				try {

					fileWriter = new FileWriter("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\sharedvariable\\Critical.txt",true);
					fileWriter.write(bos.toString());
					fileWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fileWriter!=null){
						try{
							fileWriter.close();
						} catch (IOException e){
							e.printStackTrace();
						}
					}
				}
			}

			//build SMT constraints over the trace and search alternative prefixes
			explore.execute(traceObj, schedule_prefix);
			ExploreSeedInterleavings.memUsed += ExploreSeedInterleavings.memSize(ExploreSeedInterleavings.mapPrefixEquivalent);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally {
//			if (Configuration.DEBUG) {

				System.out.println(System.currentTimeMillis()+String.format(" %tT", Calendar.getInstance())+"  Exploration Done with this trace! >>\n\n" );
//			}
		}
	}
}
