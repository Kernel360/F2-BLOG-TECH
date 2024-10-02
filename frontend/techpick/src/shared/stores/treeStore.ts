import { create } from 'zustand';
import { NodeData } from '@/shared/types';
import { dynamicMockData } from '@/shared/const/mockdata';
import { NodeApi } from 'react-arborist';

interface TreeState {
  treeData: NodeData[];
  focusedNode: NodeApi | null;
  prevFocusedNode: NodeApi | null;
  focusedFolderNodeList: NodeApi[];
  focusedLinkNodeList: NodeApi[];
  setTreeData: (data: NodeData[]) => void;
  setFocusedNode: (node: NodeApi | null) => void;
  setPrevFocusedNode: (node: NodeApi | null) => void;
  setFocusedFolderNodeList: (node: NodeApi[]) => void;
  setFocusedLinkNodeList: (node: NodeApi[]) => void;
}

export const useTreeStore = create<TreeState>((set) => ({
  treeData: dynamicMockData,
  focusedNode: null,
  prevFocusedNode: null,
  focusedFolderNodeList: [],
  focusedLinkNodeList: [],

  setTreeData: (data) => set({ treeData: data }),
  setFocusedNode: (node) => set({ focusedNode: node }),
  setPrevFocusedNode: (node) => set({ prevFocusedNode: node }),
  setFocusedFolderNodeList: (node) => set({ focusedFolderNodeList: node }),
  setFocusedLinkNodeList: (node) => set({ focusedLinkNodeList: node }),
}));
