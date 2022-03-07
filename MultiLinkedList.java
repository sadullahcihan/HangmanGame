public class MultiLinkedList {
	public class ParentNode {
		private Object parentData;
		private ParentNode down;
		private ChildNode right;

		public ParentNode(Object parentData) {
			this.parentData = parentData;
			down = null;
			right = null;
		}

		public Object getParentData() {
			return parentData;
		}

		public void setParentData(Object parentData) {
			this.parentData = parentData;
		}

		public ParentNode getDown() {
			return down;
		}

		public void setDown(ParentNode down) {
			this.down = down;
		}

		public ChildNode getRight() {
			return right;
		}

		public void setRight(ChildNode right) {
			this.right = right;
		}
	}

	public class ChildNode {
		private Object childData;
		private ChildNode next;

		public ChildNode(Object childData) {
			this.childData = childData;
			next = null;
		}

		public Object getChildData() {
			return childData;
		}

		public void setChildData(Object childData) {
			this.childData = childData;
		}

		public ChildNode getNext() {
			return next;
		}

		public void setNext(ChildNode next) {
			this.next = next;
		}
	}

	private ParentNode head;

	public MultiLinkedList() {
		head = null;
	}

	public void addParent(Object dataToAdd) {
		if (head == null) {
			ParentNode newnode = new ParentNode(dataToAdd);
			head = newnode;
		} else {
			ParentNode temp = head;
			while (temp.getDown() != null)
				temp = temp.getDown();
			ParentNode newnode = new ParentNode(dataToAdd);
			temp.setDown(newnode);
		}
	}

	public void addChild(Object parent, Object dataToadd) {
		if (head == null)
			System.out.println("Add a parent before child");
		else {
			ParentNode temp = head;
			while (temp != null) {
				if (parent.equals(temp.getParentData())) {
					ChildNode temp2 = temp.getRight();
					if (temp2 == null) {
						ChildNode newnode = new ChildNode(dataToadd);
						temp.setRight(newnode);
					} else {
						while (temp2.getNext() != null)
							temp2 = temp2.getNext();
						ChildNode newnode = new ChildNode(dataToadd);
						temp2.setNext(newnode);
					}
				}
				temp = temp.getDown();
			}
		}
	}

	public void display() {
		if (head == null)
			System.out.println("Multi linked list is empty");
		else {
			ParentNode temp = head;
			while (temp != null) {
				System.out.print(temp.getParentData() + " --> ");
				ChildNode temp2 = temp.getRight();
				while (temp2 != null) {
					System.out.print(temp2.getChildData() + " ");
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
				System.out.println();
			}
		}
	}

	public void addSortedParent(Object dataToAdd) { // in order to sort parent nodes sequentially
		int wordLength = dataToAdd.toString().length(); // word length is parent node
		if (head == null) { // Adding first node
			ParentNode newnode = new ParentNode(wordLength);
			head = newnode;
		} else { // Adding between and the last of the MLL
			ParentNode temp = head;
			if (wordLength < Integer.parseInt(temp.getParentData().toString())) { // Checking first child element
				ParentNode newnode = new ParentNode(wordLength);
				newnode.setDown(head);
				head = newnode;
			} else { // other child elements
				while (temp.getDown() != null
						&& wordLength > Integer.parseInt(temp.getDown().getParentData().toString()))
					temp = temp.getDown(); // turns until correct location
				if (temp.getDown() == null) { // Adding the last
					ParentNode newnode = new ParentNode(wordLength);
					newnode.setDown(temp.getDown());
					temp.setDown(newnode);
				} else if (wordLength != Integer.parseInt(temp.getParentData().toString())
						&& wordLength != Integer.parseInt(temp.getDown().getParentData().toString())) {
					ParentNode newnode = new ParentNode(wordLength);
					newnode.setDown(temp.getDown()); // if conditions are to prevent same parents like "5-> 5->"
					temp.setDown(newnode); // Adding between and different parents
				}
			}
		}
	}

	public void addAlphebetically(Object dataToAdd) {
		addSortedParent(dataToAdd); // parents are detected sequentially
		int wordLength = dataToAdd.toString().length();
		ChildNode newnode = new ChildNode(dataToAdd);
		if (head == null)
			System.out.println("Add a parent before child");
		else {
			ParentNode temp = head;
			while (temp != null) {
				if (Integer.parseInt(temp.getParentData().toString()) == wordLength) { // if word has same length
					ChildNode temp2 = temp.getRight();
					// Compared to first element
					if (temp2 != null && ((String) dataToAdd).compareTo((String) temp2.getChildData()) <= 0) {
						newnode.setNext(temp2); // Adds first element of the length of the line
						temp.setRight(newnode);
					} else {
						if (temp2 == null) { // if the list is empty then add first;
							temp.setRight(newnode);
							break; // first element has been added.
						}
						if (temp2.getNext() != null
								&& ((String) dataToAdd).compareTo((String) temp2.getChildData()) <= 0) {
							newnode.setNext(temp2.getNext()); // compared second element (not to enter loop)
							temp2.setNext(newnode);
						} else {
							while (temp2.getNext() != null
									&& ((String) dataToAdd).compareTo((String) temp2.getNext().getChildData()) > 0) {
								temp2 = temp2.getNext(); // datatoADD compares to next element
							}
							newnode.setNext(temp2.getNext());
							temp2.setNext(newnode);
						}
					}
					break; // The word has been inserted. Hence, the method has been finished
				}
				temp = temp.getDown();
			}
		}
	}

	public String findRandomWord(int nThWord) { // Traveling MLL according to given number
		int passedWordCounter = 1; // counter of passed word
		if (head == null)
			System.out.println("There is no parent like that");
		else {
			ParentNode temp = head;
			while (temp != null) {
				ChildNode temp2 = temp.getRight();
				if (temp2 != null) {
					while (temp2 != null) {
						if (passedWordCounter == nThWord) { // nTH word is selected
							return temp2.getChildData().toString();
						}
						passedWordCounter++;
						temp2 = temp2.getNext();
					}
				}
				temp = temp.getDown();
			}
		}
		return "wordIsNotFinded"; // in case of errors
	}
}
