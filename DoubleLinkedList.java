public class DoubleLinkedList {
	public class Node {
		private Object data;
		private Node previous;
		private Node next;

		public Node(Object data) {
			this.data = data;
			previous = null;
			next = null;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public Node getPrevious() {
			return previous;
		}

		public void setPrevious(Node previous) {
			this.previous = previous;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
	}

	private Node head;
	private Node tail;

	public DoubleLinkedList() {
		head = null;
		tail = null;
	}

	public void addToTheEnd(Object dataToAdd) {
		Node newNode = new Node(dataToAdd);
		if (head == null && tail == null)
			head = tail = newNode;
		else {
			newNode.setPrevious(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
	}

	public void displayFromHead() {
		Node temp = head;
		while (temp != null) {
			System.out.print(temp.getData() + " ");
			temp = temp.getNext();
		}
		System.out.println();
	}

	public void displayFromTail() {
		Node temp = tail;
		while (temp != null) {
			System.out.print(temp.getData() + " ");
			temp = temp.getPrevious();
		}
		System.out.println();
	}

	public int size() {
		Node temp = head;
		int size = 0;
		if (temp == null)
			return size;
		else {
			while (temp != null) {
				size++;
				temp = temp.getNext();
			}
			return size;
		}
	}

	public Boolean delete(char dataToDelete) { // delete procedure and is item deleted control
		Boolean isLetterDeleted = true;
		if (dataToDelete < 'M') { // deleting process searches from the head (<M)
			Node temp = head;
			if ((char) temp.getData() == dataToDelete) { // deletes head element
				head = head.getNext();
				head.getPrevious().setNext(null);
				head.setPrevious(null);
			} else {
				while (temp != null && (char) temp.getData() != dataToDelete)
					temp = temp.getNext();
				if (temp != null) { // deleting between and last
					temp.getNext().setPrevious(temp.getPrevious());
					temp.getPrevious().setNext(temp.getNext());
				} else { // same letter control: if the word is not finded_ then it has deleted before
					isLetterDeleted = false;
					System.out.println("You entered the same letter before.");
				}
			}
		} else { // deleting process searches from the tail
			Node temp = tail;
			if ((char) temp.getData() == dataToDelete) { // deletes tail element
				tail = tail.getPrevious();
				tail.getNext().setPrevious(null);
				tail.setNext(null);
			} else {
				while (temp != null && (char) temp.getData() != dataToDelete)
					temp = temp.getPrevious();
				if (temp != null) { // deleting between and last
					temp.getPrevious().setNext(temp.getNext());
					temp.getNext().setPrevious(temp.getPrevious());
				} else { // same letter control: if the word is not finded_ then it has deleted before
					isLetterDeleted = false;
					System.out.println("You entered the same letter before.");
				}
			}
		}
		return isLetterDeleted;
	}
}