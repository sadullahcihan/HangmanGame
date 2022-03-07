public class SingleLinkedList {
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

	public void addLast(Object dataToAdd) {
		if (head == null) {
			Node newnode = new Node(dataToAdd);
			head = newnode;
		} else {
			Node temp = head;
			while (temp.getLink() != null)
				temp = temp.getLink();
			Node newnode = new Node(dataToAdd);
			temp.setLink(newnode);
		}
	}

	public void display() {
		if (head == null) {
			System.out.println("Linked list is empty...");
		} else {
			Node temp = head;
			while (temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
		}
	}

	public void addFront(Object dataToAdd) {
		Node newnode = new Node(dataToAdd);
		if (head == null)
			head = newnode;
		else {
			newnode.setLink(head);
			head = newnode;
		}
	}

	public void addSorted(Object dataToAdd) { // sorts high score table according to their points
		int score = Integer.parseInt(dataToAdd.toString().split(";")[1]);
		if (head == null) {
			Node newnode = new Node(dataToAdd);
			head = newnode;
		} else {
			Node temp = head;
			if (score > Integer.parseInt(((String) temp.getData()).split(";")[1]))
				addFront(dataToAdd);// Compared to first head element
			else { // Adds between and last of the SLL
				while (temp.getLink() != null
						&& score <= Integer.parseInt(((String) temp.getLink().getData()).split(";")[1])) {
					temp = temp.getLink(); // datatoADD compares to next element
				}
				Node newnode = new Node(dataToAdd);
				newnode.setLink(temp.getLink());
				temp.setLink(newnode);
			}
		}
	}

	public void deleteLast() { // 11th player is deleted
		if (head == null) {
			System.out.println("Linked list is empty...");
		} else {
			Node temp = head;
			while (temp.getLink().getLink() != null) {
				temp = temp.getLink();
			}
			temp.setLink(null); // deletes last link and garbage collection deletes their data
		}
	}

	public Node getHead() {
		return head;
	}
}