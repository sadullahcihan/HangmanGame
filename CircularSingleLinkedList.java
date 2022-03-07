public class CircularSingleLinkedList {
	public class Node {
		private Object data;
		private Node link;

		public Node(Object dataToAdd) {
			data = dataToAdd;
			link = null;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public Node getLink() {
			return link;
		}

		public void setLink(Node link) {
			this.link = link;
		}
	}

	private Node head;

	public CircularSingleLinkedList() {
		head = null;
	}

	public void add(Object dataToAdd) {
		if (head == null) {
			Node newnode = new Node(dataToAdd);
			head = newnode;
			head.setLink(head);
		} else { // add to the beginning of the list
			Node temp = head;
			do
				temp = temp.getLink();
			while (temp.getLink() != head);
			Node newnode = new Node(dataToAdd);
			newnode.setLink(head);
			temp.setLink(newnode);
			head = newnode;
		}
	}

	public void display() {
		if (head == null) {
			System.out.println("Circular Linked list is empty...");
		} else {
			Node temp = head;
			do {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			} while (temp != head);
			System.out.println();
		}
	}

	public int size() {
		int size = 0;
		if (head == null) {
			System.out.println("Circular Linked list is empty...");
		} else {
			Node temp = head;
			do {
				size++;
				temp = temp.getLink();
			} while (temp != head);
		}
		return size;
	}

	public Boolean search(char dataToFind) { // searching process
		Boolean isFinded = false;
		if (head == null) {
			System.out.println("Circular Linked list is empty...");
		} else {
			Node temp = head;
			do {
				if (dataToFind == (char) temp.getData()) // if item is finded_then flag is true
					isFinded = true;
				temp = temp.getLink();
			} while (temp != head);
		}
		return isFinded;
	}

	public Node getHead() {
		return head;
	}

	public void change(CircularSingleLinkedList answerCSLL, char inputLetter) { // it changes each correct letter
		if (head == null) {
			System.out.println("Circular Linked list is empty...");
		} else {
			Node temp = head; // displayed CSLL (CSLL2)
			Node temp2 = answerCSLL.getHead(); // answer CSLL (CSLL1)
			do {
				if (inputLetter == (char) temp2.getData())  // if the letter equals answer, dashes will change
					temp.setData(inputLetter);
				temp = temp.getLink();  // in order to travel synchronously in both CSLLs
				temp2 = temp2.getLink();
			} while (temp != head || temp2 != answerCSLL.getHead());
		}
	}
}