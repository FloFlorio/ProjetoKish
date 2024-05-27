public class AVL {

    private final BinaryTree avltree;

    public AVL(BinaryTree binaryTree) {
        this.avltree = binaryTree;
    }

    public void setRoot(Node root) {
        avltree.setRoot(root);
    }

    private void insertNodeAVL(Node root, Node newNode) {
        if (root == null) {
            return; 
        }

        if (newNode.getToken().getId() < root.getToken().getId()) {
            if (root.getLeft() == null) {
                root.setLeft(newNode);
            } else {
                insertNodeAVL(root.getLeft(), newNode);
            }
        } else if (newNode.getToken().getId() > root.getToken().getId()) {
            if (root.getRight() == null) {
                root.setRight(newNode);
            } else {
                insertNodeAVL(root.getRight(), newNode);
            }
        } else {
            // O novo nó já existe na árvore, não é necessário inserir novamente
            System.out.println("O nó " + newNode.getToken().getValue() + " já existe na árvore.");
        }
    }

    private Node deleteNode(Node root, int id) {
        if(root == null){return root;}
        // Se o id a ser deletado é menor do que a raiz, vá para a subárvore esquerda
        if (id < root.getToken().getId()) {
            root.setLeft(deleteNode(root.getLeft(), id));
        }
        // Se o id a ser deletado é maior do que a raiz, vá para a subárvore direita
        else if (id > root.getToken().getId()) {
            root.setRight(deleteNode(root.getRight(), id));
        }
        // Se o id é o mesmo que o id da raiz, este é o nó a ser deletado
        else {
            // Nó com apenas um filho ou sem filhos
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            }

            // Nó com dois filhos: Obtenha o sucessor (menor na subárvore direita)
            root.setToken(nodeSucessor(root.getRight()).getToken());

            // Delete o sucessor
            root.setRight(deleteNode(root.getRight(), root.getToken().getId()));
        }
        return root;
    }

    public void updateNode(Node root, int id, String newValue) {
        updateNodeValue(root, id, newValue);
    }

    private void updateNodeValue(Node root, int id, String newValue) {
        if (root == null) {
            System.out.println("Nó com o ID " + id + " não encontrado na árvore.");
            return;
        }

        if (id < root.getToken().getId()) {
            updateNodeValue(root.getLeft(), id, newValue);
        } else if (id > root.getToken().getId()) {
            updateNodeValue(root.getRight(), id, newValue);
        } else {
            // Encontrou o nó com o ID desejado
            root.getToken().setValue(newValue);
            System.out.println("Nó com o ID " + id + " alterado para o valor: " + newValue);
        }
    }
    public void insertBalance(Node root, Node newNode) {
        insertNodeAVL(root, newNode);
        avltree.balance(root);
    }

    public Node deleteBalance(Node root, int id) {
        Node newRoot = deleteNode(root, id);
        avltree.balance(newRoot);
        return newRoot;
    }

    public void updateBalance(Node root, int id, String newValue) {
        updateNodeValue(root, id, newValue);
        avltree.balance(root);
    }

    private Node nodeSucessor(Node node) {
        Node current = node;
        // Encontre o nó mais à esquerda
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }
}
