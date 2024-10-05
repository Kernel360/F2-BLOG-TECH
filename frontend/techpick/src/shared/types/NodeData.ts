import { NodeApi } from 'react-arborist';
import { CSSProperties } from 'react';

export interface NodeData {
  id: string;
  type: 'folder' | 'pick';
  children?: NodeData[];
  name: string;
  folderId?: number; // folder에만 적용
  pickId?: number; // pick에만 적용
}

export interface DirectoryNodeProps {
  node: NodeApi;
  style: CSSProperties;
  dragHandle?: (el: HTMLDivElement | null) => void;
}
