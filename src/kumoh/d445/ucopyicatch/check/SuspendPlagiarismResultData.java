package kumoh.d445.ucopyicatch.check;

import java.util.ArrayList;

public class SuspendPlagiarismResultData {
	private ArrayList<SuspendPlagiarismResultItemData> result = new ArrayList<SuspendPlagiarismResultItemData>();;
	
	public ArrayList<SuspendPlagiarismResultItemData> getResult() {
		return result;
	}
	
	public boolean isOriginalIndex(int number) {
		for(int i = 0; i < result.size() ; i++) {
			if(result.get(i).getPartOfOriginalDocIndex()==number) {
				return true;
			}
		}
		return false;
	}
	
	public int getSuspendPlagiarismResultItemDataIndex(int number) {
		int i;
		for(i = 0; i < result.size() ; i++) {
			if(result.get(i).getPartOfOriginalDocIndex()==number) {
				break;
			}
		}
		return i;
	}
}
